package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.admin.ApproveHouseRequestDto;
import ku.cs.project_SE.dto.admin.RejectHouseRequestDto;
import ku.cs.project_SE.dto.house.HouseRequestDto;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.HouseRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//Tee
@RestController
@RequestMapping("/admin")
public class HouseRequestManagementController {

    HouseRequestService houseRequestService;

    public HouseRequestManagementController(HouseRequestService houseRequestService) {
        this.houseRequestService = houseRequestService;
    }


    @GetMapping("/house-request")
    public ResponseEntity<?> getHouseRequests() {
        List<HouseRequestDto> houseRequestDtoList = houseRequestService.getAllRequests();
        return ResponseEntity.ok(houseRequestDtoList);
    }


    @GetMapping("/house-request/history")
    public ResponseEntity<?> getHouseRequestsHistory() {
        List<HouseRequestDto> houseRequestDtoList = houseRequestService.getApprovedOrRejectedRequest();
        return ResponseEntity.ok(houseRequestDtoList);
    }

    @GetMapping("/house-request/{id}")
    public ResponseEntity<?> getHouseRequestById(@PathVariable UUID id) {
        HouseRequestDto dto = houseRequestService.getRequestById(id);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/house-request/approve")
    public ResponseEntity<String> approve(@RequestBody ApproveHouseRequestDto dto, @AuthenticationPrincipal User user) {
        houseRequestService.approve(dto,user);
        System.out.println("USER: " + (user == null ? "NULL" : user.getEmail() + " | ROLE=" + user.getRole()));
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/house-request/reject")
    public ResponseEntity<String> reject(@RequestBody RejectHouseRequestDto dto, @AuthenticationPrincipal User user) {
        houseRequestService.reject(dto,user);
        return ResponseEntity.ok("ok");
    }

    

}
