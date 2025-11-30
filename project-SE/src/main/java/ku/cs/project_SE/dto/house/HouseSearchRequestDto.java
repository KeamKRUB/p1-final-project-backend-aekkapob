package ku.cs.project_SE.dto.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseSearchRequestDto {
    private String query;
    private Integer page = 1;
    private Integer pageSize = 6;
    private HouseSearchFilterRequestDto filterRequest;
}
