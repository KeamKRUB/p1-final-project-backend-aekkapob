package ku.cs.project_SE.model.filter.action.house;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.MultiSelectionDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProvinceFilter implements EntityFilter<House, MultiSelectionDTO> {
    @Override
    public Specification<House> toSpecification(MultiSelectionDTO specificationValue) {
        return ((root, query, criteriaBuilder) -> {
            List<String> selectedProvinces = specificationValue.selectedChoices();

            // If no provinces selected, return all
            if (selectedProvinces == null || selectedProvinces.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Filter by province in address.province field
            return root.get("address").get("province").in(selectedProvinces);
        });
    }
}
