package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.List;

public class DropdownFilterSpecification implements FilterSpecification {
    private final String filterSpecificationName;
    private final List<String> filterChoices;
    private final String defaultValue;

    public DropdownFilterSpecification(String filterName, List<String> choices, String defaultValue) {
        this.filterSpecificationName = filterName;
        this.filterChoices = choices;
        this.defaultValue = defaultValue;
    }

    public DropdownFilterSpecification(String filterName, List<String> choices) {
        this(filterName, choices, null);
    }

    @Override
    public String getFilterSpecificationName() {
        return filterSpecificationName;
    }

    @Override
    public String getFilterSpecificationTypeName() {
        return "DropdownFilter";
    }

    @Override
    public JSONObject getFilterSpecificationJSON() {
        JSONObject filterSpecificationJSON = new JSONObject();
        filterSpecificationJSON.put("choices",this.filterChoices);
        if (this.defaultValue != null){
            filterSpecificationJSON.put("defaultValue",this.defaultValue);
        }
        return filterSpecificationJSON;
    }
}
