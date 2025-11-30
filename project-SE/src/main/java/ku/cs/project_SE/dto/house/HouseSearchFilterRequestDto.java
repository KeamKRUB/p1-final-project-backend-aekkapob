package ku.cs.project_SE.dto.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseSearchFilterRequestDto {
    private List<FilterCriteriaDto> filters = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterCriteriaDto {
        private String filterName;
        private Map<String, Object> filterData;
    }
}
