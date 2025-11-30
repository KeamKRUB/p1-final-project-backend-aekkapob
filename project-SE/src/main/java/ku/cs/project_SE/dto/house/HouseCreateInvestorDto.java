package ku.cs.project_SE.dto.house;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.enums.house.HouseStatus;
import ku.cs.project_SE.enums.house.HouseType;

import java.util.List;

public record HouseCreateInvestorDto(
        String houseName,
        HouseType houseType,
        String price,
        String houseDescription,
        List<Long> imageId,
        String purposedProjectName,
        String caption,
        Address address,
        Integer bedrooms,
        Integer bathrooms,
        Double squareFootage,
        Integer yearBuilt,
        Integer garageSpaces,
        String lawInformation,
        String structure
) {

}
