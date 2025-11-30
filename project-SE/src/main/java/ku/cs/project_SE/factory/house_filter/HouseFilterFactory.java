package ku.cs.project_SE.factory.house_filter;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.model.filter.specification.FilterSpecification;
import ku.cs.project_SE.model.filter.specification.SpecificationDataDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseFilterFactory {
    private final HouseFilterRegistry filterRegistry;

    @Autowired
    public HouseFilterFactory(HouseFilterRegistry filterRegistry) {
        this.filterRegistry = filterRegistry;
    }

    public List<FilterSpecification> getHouseFilterSpecifications() {
        return filterRegistry.getAllFilterSpecifications();
    }

    /**
     * Get filter specifications based on user role
     * @param user the user for whom to get filters (can be null for basic filters)
     * @return list of filter specifications appropriate for the user's role
     */
    public List<FilterSpecification> getHouseFilterSpecifications(User user) {
        return filterRegistry.getFilters(user);
    }

    public Specification<House> getFilterSpecification(String filterName, JSONObject filterData) {
        FilterRegistryEntry<House, ?> entry = filterRegistry.getFilterEntry(filterName);

        if (entry == null) {
            return null;
        }

        return createSpecification(entry, filterData);
    }

    /**
     * Checks if a user has permission to use a specific filter
     * @param filterName The name of the filter to check
     * @param user The user to check permissions for (can be null)
     * @return true if the user can use the filter, false otherwise
     */
    public boolean canUserUseFilter(String filterName, User user) {
        if (filterName == null) {
            return false;
        }

        // Get all filters available to this user
        List<FilterSpecification> userFilters = getHouseFilterSpecifications(user);

        // Check if the requested filter is in the user's allowed filters
        return userFilters.stream()
                .anyMatch(filter -> filterName.equals(filter.getFilterSpecificationName()));
    }

    private <D extends SpecificationDataDTO> Specification<House> createSpecification(
            FilterRegistryEntry<House, D> entry,
            JSONObject filterData
    ) {
        try {
            D dto = entry.createDtoFromJson(filterData);

            return entry.getEntityFilter().toSpecification(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create specification for filter: " + entry.getFilterName(), e);
        }
    }
}
