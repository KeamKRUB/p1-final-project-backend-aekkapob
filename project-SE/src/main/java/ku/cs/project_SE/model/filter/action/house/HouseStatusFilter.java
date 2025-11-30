package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.enums.house.HouseStatus;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.MultiSelectionDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HouseStatusFilter implements EntityFilter<House, MultiSelectionDTO> {
    @Override
    public Specification<House> toSpecification(MultiSelectionDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            List<String> selectedStatuses = specificationValue.selectedChoices();

            // If no statuses selected, return all
            if (selectedStatuses == null || selectedStatuses.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Convert string selections to HouseStatus enum and filter
            return root.get("houseStatus").as(String.class).in(selectedStatuses);
        });
    }
}
