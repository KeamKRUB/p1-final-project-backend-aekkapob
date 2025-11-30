package ku.cs.project_SE.service;

import jakarta.persistence.criteria.JoinType;
import ku.cs.project_SE.dto.house.HouseSearchFilterRequestDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.house.HouseStatus;
import ku.cs.project_SE.factory.house_filter.HouseFilterFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SpecificationComposer {

    private final HouseFilterFactory houseFilterFactory;

    @Autowired
    public SpecificationComposer(HouseFilterFactory houseFilterFactory) {
        this.houseFilterFactory = houseFilterFactory;
    }

    /**
     * Creates a specification to filter houses by APPROVED status
     * This ensures only approved houses are returned in public searches
     * @return Specification for APPROVED status
     */
    private Specification<House> createApprovedStatusSpecification() {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("houseStatus"), HouseStatus.APPROVED);
    }

    /**
     * Creates a specification to exclude renovatable houses for non-business users
     * Non-business users (customers) should not see houses that need renovation
     * @param user The user making the request
     * @return Specification to exclude renovatable houses if user is not admin/investor, null otherwise
     */
    private Specification<House> createNonRenovatableHousesSpecification(User user) {
        // Only apply this restriction for non-business users (customers)
        boolean isBusinessUser = user != null &&
            (user.getRole() == Role.ADMIN || user.getRole() == Role.INVESTOR);

        if (isBusinessUser) {
            return null; // No restriction for admin/investor users
        }

        // For customers, exclude houses that have a HouseRenovate entry
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.isNull(root.join("renovate", JoinType.LEFT).get("id"));
    }

    /**
     * Composes multiple filter specifications into a single combined specification using AND logic
     * Validates that the user has permission to use each requested filter
     * @param filterRequest The filter request containing multiple filter criteria
     * @param user The user making the request (for role-based validation)
     * @return Combined specification, or null if no filters
     */
    public Specification<House> composeSpecifications(HouseSearchFilterRequestDto filterRequest, User user) {
        if (filterRequest == null || filterRequest.getFilters() == null || filterRequest.getFilters().isEmpty()) {
            return null;
        }

        Specification<House> combinedSpec = null;

        for (HouseSearchFilterRequestDto.FilterCriteriaDto criteria : filterRequest.getFilters()) {
            String filterName = criteria.getFilterName();
            JSONObject filterData = new JSONObject(criteria.getFilterData());

            // Validate user has permission to use this filter
            if (!houseFilterFactory.canUserUseFilter(filterName, user)) {
                continue; // Skip filters that user doesn't have permission to use
            }

            Specification<House> spec = houseFilterFactory.getFilterSpecification(filterName, filterData);

            if (spec != null) {
                if (combinedSpec == null) {
                    combinedSpec = Specification.where(spec);
                } else {
                    combinedSpec = combinedSpec.and(spec);
                }
            }
        }

        return combinedSpec;
    }

    /**
     * Creates a text search specification for searching across project name, caption, and description
     * @param query The search query
     * @return Search specification, or null if query is empty
     */
    public Specification<House> createTextSearchSpecification(String query) {
        if (query == null || query.isBlank()) {
            return null;
        }

        return (root, criteriaQuery, criteriaBuilder) -> {
            String searchPattern = "%" + query.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("project").get("projectName")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("caption")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern)
            );
        };
    }

    public Specification<House> createProjectNameNotNullSpecification() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.isNotNull(root.get("project"));
        };
    }


    /**
     * Combines text search and filter specifications using AND logic
     * Always includes APPROVED status filter for public searches
     * @param query Text search query
     * @param filterRequest Filter criteria
     * @param user The user making the request (for role-based validation)
     * @return Combined specification with APPROVED status filter
     */
    public Specification<House> composeSearchAndFilters(String query, HouseSearchFilterRequestDto filterRequest, User user) {
        Specification<House> textSearchSpec = createTextSearchSpecification(query);
        Specification<House> filterSpec = composeSpecifications(filterRequest, user);
        Specification<House> approvedStatusSpec = createApprovedStatusSpecification();
        Specification<House> projectNameNotNullSpecification = createProjectNameNotNullSpecification();
        Specification<House> nonRenovatableHousesSpec = createNonRenovatableHousesSpecification(user);

        Specification<House> combinedSpec = approvedStatusSpec;

        if (textSearchSpec != null) {
            combinedSpec = combinedSpec.and(textSearchSpec);
        }

        if (filterSpec != null) {
            combinedSpec = combinedSpec.and(filterSpec);
        }

        if (projectNameNotNullSpecification != null) {
            combinedSpec = combinedSpec.and(projectNameNotNullSpecification);
        }

        if (nonRenovatableHousesSpec != null) {
            combinedSpec = combinedSpec.and(nonRenovatableHousesSpec);
        }

        return combinedSpec;
    }

    /**
     * Combines text search and filter specifications using AND logic
     * Always includes APPROVED status filter for public searches
     * @deprecated Use composeSearchAndFilters(String, HouseSearchFilterRequestDto, User) instead
     * This method doesn't validate user permissions
     */
    @Deprecated
    public Specification<House> composeSearchAndFilters(String query, HouseSearchFilterRequestDto filterRequest) {
        return composeSearchAndFilters(query, filterRequest, null);
    }
}
