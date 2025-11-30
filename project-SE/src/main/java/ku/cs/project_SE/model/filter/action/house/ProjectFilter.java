package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.DropdownFilterDTO;
import ku.cs.project_SE.model.filter.specification.MultiSelectionDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProjectFilter implements EntityFilter<House, DropdownFilterDTO> {
    @Override
    public Specification<House> toSpecification(DropdownFilterDTO specificationValue) throws Exception {
        return ((root, query, criteriaBuilder) -> {
            String selectedChoice = specificationValue.selectedChoice();

            // If no areas selected, return all
            if (selectedChoice == null || selectedChoice.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Filter by area in address.area field
            return root.get("project").get("projectName").in(selectedChoice);
        });
    }
}
