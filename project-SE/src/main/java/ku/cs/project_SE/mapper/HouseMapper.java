// keam
package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.house.HouseCreateAdminDto;
import ku.cs.project_SE.dto.house.HouseEditAdminRequestDto;
import ku.cs.project_SE.dto.house.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house.HouseRental;
import ku.cs.project_SE.entity.house.HouseRenovate;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.enums.HouseRegisterStatus;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    @Mapping(target = "project", expression = "java(convertProjectNameToProject(dto.projectName()))")
    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "caption", source = "caption")
    @Mapping(target = "description", source = "houseDescription")
    @Mapping(target = "images", expression = "java(convertImageIdsToImages(dto.imageId()))")
    @Mapping(target = "houseType", expression = "java(dto.houseType() != null ? dto.houseType().name() : null)")
    @Mapping(target = "price",expression = "java((dto.price() == null || dto.price().isBlank()) ? 0.0 : Double.parseDouble(dto.price()))")
    @Mapping(target = "bedrooms", expression = "java(dto.bedrooms() != null ? dto.bedrooms() : 0)")
    @Mapping(target = "bathrooms", expression = "java(dto.bathrooms() != null ? dto.bathrooms() : 0)")
    @Mapping(target = "squareFootage", expression = "java(dto.squareFootage() != null ? dto.squareFootage() : 0.0)")
    @Mapping(target = "yearBuilt", expression = "java(dto.yearBuilt() != null ? dto.yearBuilt() : 0)")
    @Mapping(target = "garageSpaces", expression = "java(dto.garageSpaces() != null ? dto.garageSpaces() : 0)")
    @Mapping(target = "address", source = "address")
    House createByAdminToEntity(HouseCreateAdminDto dto);

    @Mapping(target = "proposedProjectName", source = "purposedProjectName")
    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "caption", source = "caption")
    @Mapping(target = "description", source = "houseDescription")
    @Mapping(target = "images", expression = "java(convertImageIdsToImages(dto.imageId()))")
    @Mapping(target = "houseType", expression = "java(dto.houseType() != null ? dto.houseType().name() : null)")
    @Mapping(target = "price", expression = "java(dto.price() != null ? Double.parseDouble(dto.price()) : 0.0)")
    @Mapping(target = "bedrooms", expression = "java(dto.bedrooms() != null ? dto.bedrooms() : 0)")
    @Mapping(target = "bathrooms", expression = "java(dto.bathrooms() != null ? dto.bathrooms() : 0)")
    @Mapping(target = "squareFootage", expression = "java(dto.squareFootage() != null ? dto.squareFootage() : 0.0)")
    @Mapping(target = "yearBuilt", expression = "java(dto.yearBuilt() != null ? dto.yearBuilt() : 0)")
    @Mapping(target = "garageSpaces", expression = "java(dto.garageSpaces() != null ? dto.garageSpaces() : 0)")
    @Mapping(target = "address", source = "address")
    House createByInvestorToEntity(HouseCreateInvestorDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "project", expression = "java(convertProjectNameToProject(dto.project()))")
    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "caption", source = "caption")
    @Mapping(target = "description", source = "houseDescription")
    @Mapping(target = "images", expression = "java(convertImageIdsToImages(dto.imageId()))")
    @Mapping(target = "houseType", source = "houseType")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "bedrooms", source = "bedrooms")
    @Mapping(target = "bathrooms", source = "bathrooms")
    @Mapping(target = "squareFootage", source = "squareFootage")
    @Mapping(target = "yearBuilt", source = "yearBuilt")
    @Mapping(target = "garageSpaces", source = "garageSpaces")
    @Mapping(target = "address", source = "address")
    void updateEntityFromEdit(HouseEditAdminRequestDto dto, @MappingTarget House house);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "proposedProjectName", source = "project")
    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "caption", source = "caption")
    @Mapping(target = "description", source = "houseDescription")
    @Mapping(target = "images", expression = "java(convertImageIdsToImages(dto.imageId()))")
    @Mapping(target = "houseType", source = "houseType")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "bedrooms", source = "bedrooms")
    @Mapping(target = "bathrooms", source = "bathrooms")
    @Mapping(target = "squareFootage", source = "squareFootage")
    @Mapping(target = "yearBuilt", source = "yearBuilt")
    @Mapping(target = "garageSpaces", source = "garageSpaces")
    @Mapping(target = "address", source = "address")
    void updateEntityFromInvestorEditDto(HouseEditInvestorDto dto, @MappingTarget House house);

    @Mapping(target = "proposedProjectName", source = "house.proposedProjectName")
    @Mapping(target = "projectName", expression = "java(convertProjectToProjectName(house.getProject()))")
    @Mapping(target = "houseName", source = "house.houseName")
    @Mapping(target = "price", expression = "java(toBigDecimal(house.getPrice()))")
    @Mapping(target = "imageId", expression = "java(convertImagesToImageIds(house.getImages()))")
    @Mapping(target = "rental", expression = "java(toRentalDto(house.getRental()))")
    @Mapping(target = "renovate", expression = "java(toRenovateDto(house.getRenovate()))")
    @Mapping(target = "registeredForVisit",
            expression = "java(isUserRegistered(house.getHouseRegisters(), userId))")
    @Mapping(target = "address", source = "house.address")
    HouseDetailDto toDetail(House house, UUID userId);

    @Mapping(target = "proposedProjectName", source = "proposedProjectName") // <-- เพิ่มบรรทัดนี้
    @Mapping(target = "projectName", expression = "java(convertProjectToProjectName(house.getProject()))") // <-- เพิ่มบรรทัดนี้
    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "price", expression = "java(toBigDecimal(house.getPrice()))")
    @Mapping(target = "imageId", expression = "java(convertImagesToImageIds(house.getImages()))")
    @Mapping(target = "rental", expression = "java(toRentalDto(house.getRental()))")
    @Mapping(target = "renovate", expression = "java(toRenovateDto(house.getRenovate()))")
    @Mapping(target = "registeredForVisit",
            expression = "java(house.getHouseRegisters() != null && !house.getHouseRegisters().isEmpty())")
    @Mapping(target = "address", source = "address")
    HouseDetailDto toDetail(House house);



    @Mapping(target = "houseName", source = "houseName")
    @Mapping(target = "houseType", expression = "java(ku.cs.project_SE.enums.house.HouseType.valueOf(entity.getHouseType()))")
    @Mapping(target = "price", expression = "java(String.valueOf(entity.getPrice()))")
    @Mapping(target = "houseDescription", source = "description")
    @Mapping(target = "imageId", expression = "java(convertImagesToImageIds(entity.getImages()))")
    @Mapping(target = "projectName", expression = "java(convertProjectToProjectName(entity.getProject()))")
    HouseCreateAdminDto toCreate(House entity);

    default BigDecimal toBigDecimal(Double d) {
        return d == null ? null : BigDecimal.valueOf(d);
    }

    default Set<Image> convertImageIdsToImages(List<Long> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) return new HashSet<>();
        return imageIds.stream().map(id -> {
            Image image = new Image();
            image.setImageId(id);
            return image;
        }).collect(Collectors.toSet());
    }

    default List<Long> convertImagesToImageIds(Set<Image> images) {
        if (images == null || images.isEmpty()) return List.of();
        return images.stream().map(Image::getImageId).collect(Collectors.toList());
    }

    default Project convertProjectNameToProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) return null;
        Project project = new Project();
        project.setProjectName(projectName);
        return project;
    }

    default String convertProjectToProjectName(Project project) {
        return project != null ? project.getProjectName() : null;
    }

    default RentalDto toRentalDto(HouseRental r) {
        if (r == null) return new RentalDto(null, null, null, null);
        return new RentalDto(
                r.getRentalYield(),
                r.getPropertyTax(),
                r.getMaintenanceCost(),
                r.getAdditionalData()
        );
    }

    default RenovateDto toRenovateDto(HouseRenovate v) {
        if (v == null) return new RenovateDto(null, null, null,null);
        return new RenovateDto(
                v.getInnovateCost(),
                v.getStructure(),
                v.getLawInformation(),
                v.getAdditionalData()
        );
    }

    default boolean isUserRegistered(java.util.Set<HouseRegister> registers, UUID userId) {
        if (userId == null || registers == null || registers.isEmpty()) {
            return false;
        }
        return registers.stream()
                .anyMatch(register -> register.getUser().getUserId().equals(userId)
                && register.getRegisterStatus() == HouseRegisterStatus.PENDING);
    }
}
