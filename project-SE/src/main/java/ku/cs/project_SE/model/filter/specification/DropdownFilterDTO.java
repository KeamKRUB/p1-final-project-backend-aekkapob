package ku.cs.project_SE.model.filter.specification;

import org.json.JSONObject;

import java.util.Objects;

public final class DropdownFilterDTO implements SpecificationDataDTO {
    private String selectedChoice;

    public DropdownFilterDTO() {}

    public DropdownFilterDTO(
            String selectedChoice
    ) {
        this.selectedChoice = selectedChoice;
    }

    public String selectedChoice() {
        return selectedChoice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DropdownFilterDTO) obj;
        return Objects.equals(this.selectedChoice, that.selectedChoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedChoice);
    }

    @Override
    public String toString() {
        return "DropdownFilterDTO[" +
                "selectedChoice=" + selectedChoice + ']';
    }

    @Override
    public void fromJSON(JSONObject filterData) {
        this.selectedChoice = filterData.getString("selectedChoice");
    }
}

