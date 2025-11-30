package ku.cs.project_SE.model.filter.specification;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MultiSelectionDTO implements SpecificationDataDTO {
    private List<String> selectedChoices;

    public MultiSelectionDTO(
            List<String> selectedChoices
    ) {
        this.selectedChoices = selectedChoices;
    }

    public MultiSelectionDTO() {}

    public List<String> selectedChoices() {
        return selectedChoices;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MultiSelectionDTO) obj;
        return Objects.equals(this.selectedChoices, that.selectedChoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedChoices);
    }

    @Override
    public String toString() {
        return "MultiSelectionDTO[" +
                "selectedChoices=" + selectedChoices + ']';
    }

    @Override
    public void fromJSON(JSONObject filterData) {
        this.selectedChoices = new ArrayList<>();
        JSONArray selectedChoices = filterData.getJSONArray("selectedChoices");

        for (int i = 0; i < selectedChoices.length(); i++) {
            JSONObject choice = selectedChoices.getJSONObject(i);
            String someValue = choice.getString("value");
            this.selectedChoices.add(someValue);
        }
    }
}
