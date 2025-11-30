// keam
package ku.cs.project_SE.dto.house;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.enums.house.HouseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record HouseDetailDto(
        Long houseId,
        String proposedProjectName,
        String projectName,
        String houseName,
        String caption,
        String houseType,
        BigDecimal price,
        String description,
        Integer bedrooms,
        Integer bathrooms,
        Double squareFootage,
        Integer yearBuilt,
        Integer garageSpaces,
        String lawInformation,
        String structure,
        List<Long> imageId,
        RentalDto rental,
        RenovateDto renovate,
        String additionalData,
        HouseStatus houseStatus,
        Address address,
        boolean registeredForVisit
) {}
