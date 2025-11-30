package ku.cs.project_SE.model.filter.action.house;

import jakarta.persistence.criteria.Predicate;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.MultiSelectionDTO;
import ku.cs.project_SE.model.filter.specification.MultiSelectionFilterSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AreaFilter implements EntityFilter<House, MultiSelectionDTO> {
    @Override
    public Specification<House> toSpecification(MultiSelectionDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            List<String> selectedAreas = specificationValue.selectedChoices();

            // If no areas selected, return all
            if (selectedAreas == null || selectedAreas.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Filter by area in address.area field
            return root.get("address").get("area").in(selectedAreas);
        });
    }
}
