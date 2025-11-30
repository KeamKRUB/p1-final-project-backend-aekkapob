package ku.cs.project_SE.dto.house;

public record LocationDto(
        String address,
        String province,
        String district,
        String subdistrict,
        String postalCode,
        Double lat,
        Double lon

) {
}
