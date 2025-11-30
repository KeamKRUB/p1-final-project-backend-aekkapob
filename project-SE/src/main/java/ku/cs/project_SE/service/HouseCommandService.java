// ku/cs/project_SE/service/HouseService.java
package ku.cs.project_SE.service;

import jakarta.transaction.Transactional;
import ku.cs.project_SE.dto.house.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house.HouseRenovate;
import ku.cs.project_SE.entity.house.HouseRental;
import ku.cs.project_SE.entity.house.HouseRequest;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.house.HouseStatus;
import ku.cs.project_SE.mapper.HouseMapper;
import ku.cs.project_SE.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
//Tee
public class HouseCommandService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;
    private final ImageRepository imageRepository;
    private final ProjectRepository projectRepository;
    private final HouseRentalRepository houseRentalRepository;
    private final HouseRenovateRepository houseRenovateRepository;
    private final HouseRequestRepository houseRequestRepository;

    public HouseCommandService(HouseRepository houseRepository, HouseMapper houseMapper,
                               ImageRepository imageRepository, ProjectRepository projectRepository,
                               HouseRentalRepository houseRentalRepository, HouseRenovateRepository houseRenovateRepository,HouseRequestRepository houseRequestRepository) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
        this.imageRepository = imageRepository;
        this.projectRepository = projectRepository;
        this.houseRentalRepository = houseRentalRepository;
        this.houseRenovateRepository = houseRenovateRepository;
        this.houseRequestRepository = houseRequestRepository;
    }

    public House createHouseFromAdmin(HouseCreateAdminDto adminDto, User adminUser) {
        System.out.println(">> Incoming projectName = " + adminDto.projectName());
        House house = houseMapper.createByAdminToEntity(adminDto);
        System.out.println(">> After mapping project = " + house.getProject());

        house.setHouseStatus(HouseStatus.APPROVED);

        return finalizeAndSaveHouse(house, adminUser, adminDto.imageId(), adminDto.projectName());
    }

    public House createHouseFromInvestor(HouseCreateInvestorDto investorDto, User investorUser) {
        House house = houseMapper.createByInvestorToEntity(investorDto);
        house.setHouseStatus(HouseStatus.PENDING); // สถานะสำหรับ Investor คือรอดำเนินการ
        return finalizeAndSaveHouse(house, investorUser, investorDto.imageId(), investorDto.purposedProjectName());
    }

    private House finalizeAndSaveHouse(House house, User owner, List<Long> imageIds, String projectNameStr) {
        house.setOwner(owner);

        if (projectNameStr != null && !projectNameStr.isEmpty() && owner.getRole()== Role.ADMIN) {
            Project realProject = projectRepository.findById(projectNameStr)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectNameStr));
            house.setProject(realProject);
        }

        if (imageIds != null && !imageIds.isEmpty()) {
            Set<Image> realImages = new HashSet<>(imageRepository.findAllById(imageIds));
            if (realImages.size() != imageIds.size()) {
                throw new IllegalArgumentException("One or more images not found.");
            }
            house.setImages(realImages);
        }

        if (house.getAddress() != null) {
            house.getAddress().setId(null); // บังคับให้สร้าง Address ใหม่เสมอ
        }

        return houseRepository.save(house);
    }

    public Boolean editHouse(HouseEditAdminRequestDto dto, long id) {
        House house = houseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("House not found: id=" + id));
        houseMapper.updateEntityFromEdit(dto,house);

        // Fetch real Project entity from database
        if (house.getProject() != null && house.getProject().getProjectName() != null) {
            Project realProject = projectRepository.findById(house.getProject().getProjectName())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + house.getProject().getProjectName()));
            house.setProject(realProject);
        }

        // Fetch real Image entities from database
        if (house.getImages() != null && !house.getImages().isEmpty()) {
            Set<Image> realImages = new HashSet<>();
            for (Image image : house.getImages()) {
                if (image.getImageId() != null) {
                    Image realImage = imageRepository.findById(image.getImageId())
                            .orElseThrow(() -> new IllegalArgumentException("Image not found: " + image.getImageId()));
                    realImages.add(realImage);
                }
            }
            house.setImages(realImages);
        }

        // Handle Address detached entity issue during edit
        if (house.getAddress() != null && house.getAddress().getId() != null) {
            // During edit, check if we need to create a new address or update existing
            house.getAddress().setId(null);
        }

        houseRepository.save(house);
        return true;

    }


    public House editHouseInvestor(HouseEditInvestorDto dto, UUID requestId) {
        HouseRequest houseRequest = houseRequestRepository.findById(requestId).orElseThrow();
        House house = houseRequest.getHouse();
        houseMapper.updateEntityFromInvestorEditDto(dto,house);
        house.setProject(null);

        // Fetch real Image entities from database
        if (house.getImages() != null && !house.getImages().isEmpty()) {
            Set<Image> realImages = new HashSet<>();
            for (Image image : house.getImages()) {
                if (image.getImageId() != null) {
                    Image realImage = imageRepository.findById(image.getImageId())
                            .orElseThrow(() -> new IllegalArgumentException("Image not found: " + image.getImageId()));
                    realImages.add(realImage);
                }
            }
            house.setImages(realImages);
        }

        // Handle Address detached entity issue during edit
        if (house.getAddress() != null && house.getAddress().getId() != null) {
            // During edit, check if we need to create a new address or update existing
            house.getAddress().setId(null);
        }
        house.setHouseStatus(HouseStatus.PENDING);

        houseRepository.save(house);
        return house;

    }



    @Transactional
    public Boolean deleteHouse(Long houseId) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IllegalArgumentException("House not found: " + houseId));

        var reqs = houseRequestRepository.findByHouse_HouseId(houseId);
        if (!reqs.isEmpty()) {
            houseRequestRepository.deleteAll(reqs);
        }

        houseRepository.delete(house);
        return true;
    }

    @Transactional
    public boolean updateRentalData(Long houseId, RentalDto dto) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IllegalArgumentException("House not found: id=" + houseId));

        HouseRental rental = houseRentalRepository.findByHouse_HouseId(houseId)
                .orElseGet(() -> {
                    HouseRental r = new HouseRental();
                    r.setHouse(house);
                    return r;
                });

        rental.setRentalYield(dto.rentalYield());
        rental.setPropertyTax(dto.propertyTax());
        rental.setMaintenanceCost(dto.maintenanceCost());
        rental.setAdditionalData(dto.additionalData());

        houseRentalRepository.save(rental);
        return true;
    }

    @Transactional
    public Boolean updateRenovateData(Long houseId, RenovateDto dto) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new IllegalArgumentException("House not found: id=" + houseId));

        HouseRenovate renovate = houseRenovateRepository.findByHouse_HouseId(houseId)
                .orElseGet(() -> {
                    HouseRenovate v = new HouseRenovate();
                    v.setHouse(house);
                    return v;
                });

        renovate.setInnovateCost(dto.innovateCost());
        renovate.setLawInformation(dto.lawInformation());
        renovate.setStructure(dto.structure());
        renovate.setAdditionalData(dto.additionalData());

        houseRenovateRepository.save(renovate);
        return true;
    }


}
