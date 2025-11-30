package ku.cs.project_SE.factory.house_filter;

import ku.cs.project_SE.model.filter.action.EntityFilter;
import ku.cs.project_SE.model.filter.specification.FilterSpecification;
import ku.cs.project_SE.model.filter.specification.SpecificationDataDTO;
import org.json.JSONObject;

public class FilterRegistryEntry<T, D extends SpecificationDataDTO> {
    private final String filterName;
    private final FilterSpecification filterSpecification;
    private final EntityFilter<T, D> entityFilter;
    private final Class<D> dtoClass;

    public FilterRegistryEntry(
            String filterName,
            FilterSpecification filterSpecification,
            EntityFilter<T, D> entityFilter,
            Class<D> dtoClass
    ) {
        this.filterName = filterName;
        this.filterSpecification = filterSpecification;
        this.entityFilter = entityFilter;
        this.dtoClass = dtoClass;
    }

    public String getFilterName() {
        return filterName;
    }

    public FilterSpecification getFilterSpecification() {
        return filterSpecification;
    }

    public EntityFilter<T, D> getEntityFilter() {
        return entityFilter;
    }

    public Class<D> getDtoClass() {
        return dtoClass;
    }

    public D createDtoFromJson(JSONObject filterData) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
            dto.fromJSON(filterData);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create DTO instance for filter: " + filterName, e);
        }
    }
}
