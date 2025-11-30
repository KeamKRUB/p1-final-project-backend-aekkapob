package ku.cs.project_SE.dto.investor;

import ku.cs.project_SE.dto.house.LocationDto;
import ku.cs.project_SE.dto.image.ImageDto;
import ku.cs.project_SE.enums.house.HouseType;

import java.math.BigDecimal;
import java.util.List;

public record SellHouseRequestDto(
        HouseType houseType,
        BigDecimal price,
        String houseDescription,
        List<ImageDto> images,
        String projectName,
        LocationDto locationDto
) {
}
