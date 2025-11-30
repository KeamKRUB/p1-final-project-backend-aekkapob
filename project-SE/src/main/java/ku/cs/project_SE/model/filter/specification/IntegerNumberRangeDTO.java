package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.Objects;

public final class IntegerNumberRangeDTO implements SpecificationDataDTO {
    private int value;
    private int minValue;
    private int maxValue;

    public IntegerNumberRangeDTO(int value) {
        this.value = value;
        this.minValue = 0;
        this.maxValue = 0;
    }

    public IntegerNumberRangeDTO(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = 0;
    }

    public IntegerNumberRangeDTO() {
        this.value = 0;
        this.minValue = 0;
        this.maxValue = 0;
    }

    public int value() {
        return value;
    }

    public int minValue() {
        return minValue;
    }

    public int maxValue() {
        return maxValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (IntegerNumberRangeDTO) obj;
        return this.value == that.value &&
               this.minValue == that.minValue &&
               this.maxValue == that.maxValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, minValue, maxValue);
    }

    @Override
    public String toString() {
        return "IntegerNumberRangeDTO[" +
                "value=" + value +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue + ']';
    }

    @Override
    public void fromJSON(JSONObject filterData) {
        this.value = filterData.optInt("value", 0);
        this.minValue = filterData.optInt("minValue", 0);
        this.maxValue = filterData.optInt("maxValue", 0);
    }
}
