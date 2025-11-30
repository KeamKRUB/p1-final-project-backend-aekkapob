package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.List;

public class MultiSelectionFilterSpecification implements FilterSpecification {
    private final String filterSpecificationName;
    private final List<String> filterChoices;
    private final List<String> defaultChoices;

    public MultiSelectionFilterSpecification(String filterName, List<String> choices, List<String> defaultChoices) {
        this.filterSpecificationName = filterName;
        this.filterChoices = choices;
        this.defaultChoices = defaultChoices;
    }

    @Override
    public String getFilterSpecificationName() {
        return filterSpecificationName;
    }

    @Override
    public String getFilterSpecificationTypeName() {
        return "MultiSelectionFilter";
    }

    @Override
    public JSONObject getFilterSpecificationJSON() {
        JSONObject filterSpecificationJSON = new JSONObject();
        filterSpecificationJSON.put("choices",this.filterChoices);
        filterSpecificationJSON.put("defaultChoices",this.defaultChoices);
        return filterSpecificationJSON;
    }
}
