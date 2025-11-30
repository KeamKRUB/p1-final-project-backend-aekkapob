package ku.cs.project_SE.model.filter.action.house;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house.HouseRenovate;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.DropdownFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class HouseBigTypeFilter implements EntityFilter<House, DropdownFilterDTO> {
    @Override
    public Specification<House> toSpecification(DropdownFilterDTO specificationValue) {
        return (root, query, criteriaBuilder) -> {
            String selectedChoice = specificationValue.selectedChoice();

            // If no choice selected or "ทั้งหมด" selected, return all houses
            if (selectedChoice == null || selectedChoice.isEmpty() || selectedChoice.equals("ทั้งหมด")) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            if (selectedChoice.equals("รีโนเวท")) {
                // Return houses that have a HouseRenovate entry
                return criteriaBuilder.isNotNull(
                    root.join("renovate", JoinType.LEFT).get("id")
                );
            } else if (selectedChoice.equals("ไม่ต้องรีโนเวท")) {
                // Return houses that do NOT have a HouseRenovate entry
                return criteriaBuilder.isNull(
                    root.join("renovate", JoinType.LEFT).get("id")
                );
            }

            // Default case - return all houses
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
