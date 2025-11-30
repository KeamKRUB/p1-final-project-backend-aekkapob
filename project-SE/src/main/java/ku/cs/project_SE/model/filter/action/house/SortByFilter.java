package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.SingleSelectionFilterDTO;
import org.springframework.data.jpa.domain.Specification;

public class SortByFilter implements EntityFilter<House, SingleSelectionFilterDTO> {
    @Override
    public Specification<House> toSpecification(SingleSelectionFilterDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        });
    }
}
