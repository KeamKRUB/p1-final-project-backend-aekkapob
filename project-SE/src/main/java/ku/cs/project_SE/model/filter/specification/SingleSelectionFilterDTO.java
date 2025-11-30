package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.Objects;

public final class SingleSelectionFilterDTO implements SpecificationDataDTO {
    private String selectedChoices;

    public SingleSelectionFilterDTO(
            String selectedChoices
    ) {
        this.selectedChoices = selectedChoices;
    }

    public SingleSelectionFilterDTO(){}

    public String selectedChoices() {
        return selectedChoices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SingleSelectionFilterDTO) obj;
        return Objects.equals(this.selectedChoices, that.selectedChoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedChoices);
    }

    @Override
    public String toString() {
        return "SingleSelectionFilterDTO[" +
                "selectedChoices=" + selectedChoices + ']';
    }

    @Override
    public void fromJSON(JSONObject filterData) {

    }
}
