package ku.cs.project_SE.dto.house;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.enums.house.HouseType;

import java.util.List;

public record HouseEditInvestorDto(
        String houseName, //houseName
        HouseType houseType, //houseType
        String price, //price
        String houseDescription, //houseDescription
        List<Long> imageId, //imageId
        String project, //project
        String caption, //caption
        Address address, //address
        Integer bedrooms, //bedrooms
        Integer bathrooms, //bathrooms
        Double squareFootage, //squareFootage
        Integer yearBuilt, //structure
        Integer garageSpaces, //garageSpaces
        String lawInformation, //lawInformation
        String structure //yearBuilt
) {
}
