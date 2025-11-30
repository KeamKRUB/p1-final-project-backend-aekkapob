package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.house.HouseCreateAdminDto;
import ku.cs.project_SE.dto.house.HouseEditAdminRequestDto;
import ku.cs.project_SE.dto.house.RenovateDto;
import ku.cs.project_SE.dto.house.RentalDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.HouseCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/houses")
public class HouseAdminController {


    private final HouseCommandService houseCommandService;

    public HouseAdminController(HouseCommandService houseService) {
        this.houseCommandService = houseService;
    }


    @PostMapping("/create")
    public ResponseEntity<Long> createHouse(@RequestBody HouseCreateAdminDto houseCreateDto, @AuthenticationPrincipal User user) {
        try {
            System.out.println("createHouse");
            House house = houseCommandService.createHouseFromAdmin(houseCreateDto, user);
            return ResponseEntity.ok(house.getHouseId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("/{houseId}/delete")
    public ResponseEntity<String> deleteHouse(@PathVariable("houseId") Long houseId){
        try {
            this.houseCommandService.deleteHouse(houseId);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{houseId}/edit")
    public ResponseEntity<String> createHouse(
            @PathVariable Long houseId,
            @RequestBody HouseEditAdminRequestDto request) {

         try {
             houseCommandService.editHouse(request,houseId);
             return ResponseEntity.ok("Success");
         }
         catch (Exception e) {
             return ResponseEntity.badRequest().body(e.getMessage());
         }
    }

    @PostMapping("/{houseId}/update-rental-data")
    public ResponseEntity<String> updateRentalData(
            @PathVariable Long houseId,
            @RequestBody RentalDto rental
    ) {
        try {
            houseCommandService.updateRentalData(houseId, rental);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{houseId}/update-renovate-data")
    public ResponseEntity<String> updateRenovateData(
            @PathVariable Long houseId,
            @RequestBody RenovateDto renovate
    ) {
        try {
            houseCommandService.updateRenovateData(houseId, renovate);
            return ResponseEntity.ok("Success");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/houses-sell-management")
    public ResponseEntity<String> approve(){
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/houses-sell-management")
    public ResponseEntity<String> getHouseRequest(){
        return ResponseEntity.ok("Success");
    }
}
