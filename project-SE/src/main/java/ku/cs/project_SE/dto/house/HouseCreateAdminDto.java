package ku.cs.project_SE.dto.house;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.enums.house.HouseType;
import ku.cs.project_SE.enums.house.HouseStatus;

import java.util.List;
//Tee
public record HouseCreateAdminDto(
        String houseName,
        HouseType houseType,
        String price,
        String houseDescription,
        List<Long> imageId,
        String projectName,
        String caption,
        Address address,
        Integer bedrooms,
        Integer bathrooms,
        Double squareFootage,
        Integer yearBuilt,
        Integer garageSpaces,
        String lawInformation,
        HouseStatus houseStatus,
        String structure
) {}

