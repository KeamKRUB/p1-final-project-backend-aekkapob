package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.SingleSelectionFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class HouseTypeFilter implements EntityFilter<House, SingleSelectionFilterDTO> {
    @Override
    public Specification<House> toSpecification(SingleSelectionFilterDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            String selectedType = specificationValue.selectedChoices();

            // If no type selected or empty, return all
            if (selectedType == null || selectedType.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Filter by house type
            return criteriaBuilder.equal(root.get("houseType"), selectedType);
        });
    }
}
