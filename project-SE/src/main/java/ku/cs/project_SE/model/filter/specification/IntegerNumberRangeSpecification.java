package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

public class IntegerNumberRangeSpecification implements FilterSpecification {
    private final String filterSpecificationName;
    private final int minValue;
    private final int maxValue;
    private final String minValueLabel;
    private final String maxValueLabel;
    private final String valueUnitName;
    private final int defaultMinValue;
    private final int defaultMaxValue;

    public IntegerNumberRangeSpecification(String filterSpecificationName, int defaultMinValue, int defaultMaxValue, int minValue, int maxValue, String minValueLabel, String maxValueLabel, String valueUnitName) {
        this.filterSpecificationName = filterSpecificationName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minValueLabel = minValueLabel;
        this.maxValueLabel = maxValueLabel;
        this.valueUnitName = valueUnitName;
        this.defaultMinValue = defaultMinValue;
        this.defaultMaxValue = defaultMaxValue;
    }

    @Override
    public String getFilterSpecificationName() {
        return this.filterSpecificationName;
    }

    @Override
    public String getFilterSpecificationTypeName() {
        return "IntegerNumberRangeFilter";
    }

    @Override
    public JSONObject getFilterSpecificationJSON() {
        JSONObject filterSpecificationJSON = new JSONObject();
        filterSpecificationJSON.put("filterSpecificationName", this.filterSpecificationName);
        filterSpecificationJSON.put("defaultMinValue", this.defaultMinValue);
        filterSpecificationJSON.put("defaultMaxValue", this.defaultMaxValue);
        filterSpecificationJSON.put("minValue", this.minValue);
        filterSpecificationJSON.put("maxValue", this.maxValue);
        filterSpecificationJSON.put("minValueLabel", this.minValueLabel);
        filterSpecificationJSON.put("maxValueLabel", this.maxValueLabel);
        filterSpecificationJSON.put("valueUnitName", this.valueUnitName);
        return filterSpecificationJSON;
    }
}
