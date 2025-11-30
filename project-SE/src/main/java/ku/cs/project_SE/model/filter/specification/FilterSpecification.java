package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.function.Predicate;

public interface FilterSpecification {
    String getFilterSpecificationName();
    String getFilterSpecificationTypeName();
    JSONObject getFilterSpecificationJSON();
}
