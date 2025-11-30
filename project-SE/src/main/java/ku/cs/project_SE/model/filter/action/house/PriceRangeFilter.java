package ku.cs.project_SE.model.filter.action.house;

import jakarta.persistence.criteria.Predicate;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.IntegerNumberRangeDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PriceRangeFilter implements EntityFilter<House, IntegerNumberRangeDTO> {
    @Override
    public Specification<House> toSpecification(IntegerNumberRangeDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            int minValue = specificationValue.minValue();
            int maxValue = specificationValue.maxValue();

            List<Predicate> predicates = new ArrayList<>();

            // Add minimum price constraint if specified
            if (minValue > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), (double) minValue));
            }

            // Add maximum price constraint if specified
            if (maxValue > 0 && maxValue >= minValue) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), (double) maxValue));
            }

            // If no predicates, return all
            if (predicates.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Combine predicates with AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
