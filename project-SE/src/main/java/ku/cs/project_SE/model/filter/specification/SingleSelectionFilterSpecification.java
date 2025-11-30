package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.List;

public class SingleSelectionFilterSpecification implements FilterSpecification {
    private final String filterSpecificationName;
    private final List<String> filterChoices;

    public SingleSelectionFilterSpecification(String filterName, List<String> choices) {
        this.filterSpecificationName = filterName;
        this.filterChoices = choices;
    }

    @Override
    public String getFilterSpecificationName() {
        return filterSpecificationName;
    }

    @Override
    public String getFilterSpecificationTypeName() {
        return "SingleSelectionFilter";
    }

    @Override
    public JSONObject getFilterSpecificationJSON() {
        JSONObject filterSpecificationJSON = new JSONObject();
        filterSpecificationJSON.put("choices",this.filterChoices);
        return filterSpecificationJSON;
    }
}
