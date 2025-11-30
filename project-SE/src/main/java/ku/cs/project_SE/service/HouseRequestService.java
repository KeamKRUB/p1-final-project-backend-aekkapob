package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.admin.ApproveHouseRequestDto;
import ku.cs.project_SE.dto.admin.RejectHouseRequestDto;
import ku.cs.project_SE.dto.house.HouseRequestDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house.HouseRequest;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.ActionType;
import ku.cs.project_SE.enums.house.HouseStatus;
import ku.cs.project_SE.mapper.HouseRequestMapper;
import ku.cs.project_SE.repository.HouseRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Tee
@Service
public class HouseRequestService {
    HouseRequestRepository houseRequestRepository;
    HouseRequestMapper houseRequestMapper;
    GenericLogService genericLogService;

    public HouseRequestService(HouseRequestRepository houseRequestRepository, HouseRequestMapper houseRequestMapper, GenericLogService genericLogService) {
        this.houseRequestRepository = houseRequestRepository;
        this.houseRequestMapper = houseRequestMapper;
        this.genericLogService = genericLogService;
    }

    public HouseRequestDto getRequestById(UUID id) {
        HouseRequest request = houseRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return houseRequestMapper.toDto(request);
    }


    public Boolean CreateSellHouseRequest(House house, User user) {
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setHouse(house);
        houseRequest.setRequester(user);
        if (user.getRole().equals(Role.INVESTOR)) houseRequest.setStatus(HouseStatus.PENDING);
        else if (user.getRole().equals(Role.ADMIN)) houseRequest.setStatus(HouseStatus.APPROVED);

        houseRequest.setSubmittedAt(LocalDateTime.now());

        HouseRequest savedHouseRequest = houseRequestRepository.save(houseRequest);
        genericLogService.logAction(savedHouseRequest, ActionType.CREATED,user);
        return true;
    }

    public  List<HouseRequestDto> getHouseRequestByOwner(UUID userId) {
        List<HouseRequest> houseRequests = this.houseRequestRepository.findHouseRequestsByRequester_UserId_AndStatusNot(userId,HouseStatus.ABANDON);
        List<HouseRequestDto> houseRequestDtos = new ArrayList<>();
        for (HouseRequest houseRequest : houseRequests) {
            HouseRequestDto houseRequestDto = houseRequestMapper.toDto(houseRequest);
            houseRequestDtos.add(houseRequestDto);
        }
        return houseRequestDtos;
    }

    public List<HouseRequestDto> getAllRequests() {
        List<HouseRequest> houseRequests = houseRequestRepository.findAll();
        List<HouseRequestDto> dtos = new ArrayList<>();
        for (HouseRequest req : houseRequests) {
            dtos.add(houseRequestMapper.toDto(req));
        }
        return dtos;
    }


    public List<HouseRequestDto> getPendingAndModifiedRequests() {
        List<HouseRequest> houseRequests = houseRequestRepository.findByStatusOrStatus(HouseStatus.PENDING,HouseStatus.MODIFIED);
        List<HouseRequestDto> houseRequestDtos = new ArrayList<>();
        for (HouseRequest houseRequest : houseRequests) {
            HouseRequestDto houseRequestDto = houseRequestMapper.toDto(houseRequest);
            houseRequestDtos.add(houseRequestDto);
        }
        return houseRequestDtos;
    }

    public List<HouseRequestDto> getApprovedOrRejectedRequest() {
        List<HouseRequest> houseRequests = houseRequestRepository.findByStatusOrStatus(HouseStatus.APPROVED,HouseStatus.REJECTED);
        List<HouseRequestDto> houseRequestDtos = new ArrayList<>();
        for (HouseRequest houseRequest : houseRequests) {
            HouseRequestDto houseRequestDto = houseRequestMapper.toDto(houseRequest);
            houseRequestDtos.add(houseRequestDto);
        }
        return houseRequestDtos;
    }

    public HouseRequestDto getHouseRequestByRequestId(UUID uuid) {
        HouseRequest houseRequest = houseRequestRepository.findById(uuid).orElseThrow();
        return houseRequestMapper.toDto(houseRequest);
    }

    public void approve(ApproveHouseRequestDto dto, User user) {
        HouseRequest houseRequest = houseRequestRepository.findById(dto.requestId()).orElseThrow();
        houseRequest.setStatus(HouseStatus.APPROVED);
        houseRequest.setApprover(user);
        houseRequest.setAdminComment(dto.adminComment());
        houseRequest.setReviewedAt(LocalDateTime.now());
        houseRequest.getHouse().setHouseStatus(HouseStatus.APPROVED);
        HouseRequest savedHouseRequest = houseRequestRepository.save(houseRequest);
        genericLogService.logAction(savedHouseRequest, ActionType.APPROVED,user);

    }

    public void reject(RejectHouseRequestDto dto, User user) {
        HouseRequest houseRequest = houseRequestRepository.findById(dto.requestId()).orElseThrow();
        houseRequest.setStatus(HouseStatus.REJECTED);
        houseRequest.setApprover(user);
        houseRequest.setAdminComment(dto.adminComment());
        houseRequest.setReviewedAt(LocalDateTime.now());
        houseRequest.getHouse().setHouseStatus(HouseStatus.REJECTED);
        HouseRequest savedHouseRequest = houseRequestRepository.save(houseRequest);
        genericLogService.logAction(savedHouseRequest, ActionType.REJECTED,user);
    }


    public void editHouseRequest(UUID requestId, House house, User user) {
        HouseRequest houseRequest = houseRequestRepository.findById(requestId).orElseThrow();
        if (houseRequest.getStatus().equals(HouseStatus.APPROVED)) {
            HouseRequest newRequest = new HouseRequest();
            newRequest.setHouse(house);
            newRequest.setRequester(user);
            newRequest.setStatus(HouseStatus.PENDING);
            newRequest.setSubmittedAt(LocalDateTime.now());

            HouseRequest savedHouseRequest = houseRequestRepository.save(newRequest);
            genericLogService.logAction(savedHouseRequest, ActionType.MODIFIED,user);
            houseRequest.setStatus(HouseStatus.ABANDON);
        }
        houseRequestRepository.save(houseRequest);
        genericLogService.logAction(houseRequest, ActionType.MODIFIED,user);


    }
}
