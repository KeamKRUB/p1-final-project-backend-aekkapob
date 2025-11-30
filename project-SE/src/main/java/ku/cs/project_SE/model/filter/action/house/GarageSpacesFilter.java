package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.DropdownFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class GarageSpacesFilter implements EntityFilter<House, DropdownFilterDTO> {
    @Override
    public Specification<House> toSpecification(DropdownFilterDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            String selectedChoice = specificationValue.selectedChoice();

            // If no choice, empty, or "ทั้งหมด" (all), return all
            if (selectedChoice == null || selectedChoice.isEmpty() || selectedChoice.equals("ทั้งหมด")) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Handle "4+" case - 4 or more garage spaces
            if (selectedChoice.equals("4+")) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("garageSpaces"), 4);
            }

            // Parse the number and filter by exact garage space count
            try {
                int garageCount = Integer.parseInt(selectedChoice);
                return criteriaBuilder.equal(root.get("garageSpaces"), garageCount);
            } catch (NumberFormatException e) {
                // If parsing fails, return all
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        });
    }
}
