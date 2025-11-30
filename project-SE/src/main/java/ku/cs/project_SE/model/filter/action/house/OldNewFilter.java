package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.DropdownFilterDTO;
import ku.cs.project_SE.model.filter.specification.SingleSelectionFilterDTO;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

public class OldNewFilter implements EntityFilter<House, DropdownFilterDTO> {
    @Override
    public Specification<House> toSpecification(DropdownFilterDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            String selectedChoice = specificationValue.selectedChoice();

            // If no choice or empty, return all
            if (selectedChoice == null || selectedChoice.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            int currentYear = Year.now().getValue();

            // "มือหนึ่ง" (new/first-hand) - houses built in current or previous year
            if (selectedChoice.equals("มือหนึ่ง")) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("yearBuilt"), currentYear - 1);
            }
            // "มือสอง" (used/second-hand) - houses built before previous year
            else if (selectedChoice.equals("มือสอง")) {
                return criteriaBuilder.lessThan(root.get("yearBuilt"), currentYear - 1);
            }

            // Default: return all
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        });
    }
}
