package ku.cs.project_SE.model.filter.action;

import ku.cs.project_SE.model.filter.specification.FilterSpecification;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface EntityFilter<T,SPEC> {
    Specification<T> toSpecification(SPEC specificationValue) throws Exception;
}
