package ku.cs.project_SE.dto.house;

import java.util.List;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.enums.house.HouseStatus;

//Tee
public record HouseEditAdminRequestDto(
        String houseName,
        String project,
        String caption,
        String houseDescription,
        Address address,
        Double price,
        Integer bedrooms,
        Integer bathrooms,
        Double squareFootage,
        Integer yearBuilt,
        Integer garageSpaces,
        String houseType,
        String lawInformation,
        HouseStatus houseStatus,
        String structure,
        List<Long> imageId
//      LocationDto location  // ถ้าต้องการ map location แยก
) {}

