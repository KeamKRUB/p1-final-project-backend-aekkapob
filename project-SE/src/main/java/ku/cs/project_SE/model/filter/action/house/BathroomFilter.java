package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.DropdownFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class BathroomFilter implements EntityFilter<House, DropdownFilterDTO> {
    @Override
    public Specification<House> toSpecification(DropdownFilterDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            String selectedChoice = specificationValue.selectedChoice();

            // If no choice, empty, or "ทั้งหมด" (all), return all
            if (selectedChoice == null || selectedChoice.isEmpty() || selectedChoice.equals("ทั้งหมด")) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Handle "6+" case - 6 or more bathrooms
            if (selectedChoice.equals("6+")) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), 6);
            }

            // Parse the number and filter by exact bathroom count
            try {
                int bathroomCount = Integer.parseInt(selectedChoice);
                return criteriaBuilder.equal(root.get("bathrooms"), bathroomCount);
            } catch (NumberFormatException e) {
                // If parsing fails, return all
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        });
    }
}
