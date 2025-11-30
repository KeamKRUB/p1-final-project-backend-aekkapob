package ku.cs.project_SE.controller;


import ku.cs.project_SE.dto.house.HouseCreateInvestorDto;
import ku.cs.project_SE.dto.house.HouseEditInvestorDto;
import ku.cs.project_SE.dto.house.HouseRequestDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.HouseCommandService;
import ku.cs.project_SE.service.HouseRequestService;
import ku.cs.project_SE.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//Tee
@RestController
@RequestMapping("/investor")
public class InvestorController {
    private final HouseService houseService;
    private final HouseCommandService houseCommandService;
    private final HouseRequestService houseRequestService;

    public InvestorController(HouseService houseService,HouseCommandService houseCommandService,HouseRequestService houseRequestService) {
        this.houseService = houseService;
        this.houseCommandService = houseCommandService;
        this.houseRequestService = houseRequestService;
    }


    @PostMapping("/sell-house-propose")
    public ResponseEntity<String> create(@RequestBody HouseCreateInvestorDto dto, @AuthenticationPrincipal User user) {
        try {
            House house = houseCommandService.createHouseFromInvestor(dto,user);
            houseRequestService.CreateSellHouseRequest(house,user);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sell-house-propose/{requestId}/edit")
    public ResponseEntity<String> edit(@PathVariable UUID requestId, @RequestBody HouseEditInvestorDto dto, @AuthenticationPrincipal User user) {
        try {
            House house = houseCommandService.editHouseInvestor(dto,requestId);
            houseRequestService.editHouseRequest(requestId,house,user);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sell-house-management")
    public ResponseEntity<?> getHouseRequest(@AuthenticationPrincipal User user) {
        try {
            List<HouseRequestDto> houseRequestDtos = houseRequestService.getHouseRequestByOwner(user.getUserId());
            return ResponseEntity.ok(houseRequestDtos);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sell-house-management/{requestId}")
    public ResponseEntity<?> getHouseRequestById(@PathVariable String requestId,@AuthenticationPrincipal User user) {
        try {
            HouseRequestDto houseRequestDtos = houseRequestService.getHouseRequestByRequestId(UUID.fromString(requestId));

            return ResponseEntity.ok(houseRequestDtos);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
