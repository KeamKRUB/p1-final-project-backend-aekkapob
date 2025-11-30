package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.admin.GetSpecificHouseRegisterDto;
import ku.cs.project_SE.dto.admin.HouseRegisterApproveDto;
import ku.cs.project_SE.dto.house_register.HouseRegisterRequestDto;
import ku.cs.project_SE.dto.house_register.HouseRegisterRespondDto;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.HouseRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/register-management")
public class AdminHouseRegisterController {
    private final HouseRegisterService houseRegisterService;

    AdminHouseRegisterController(HouseRegisterService houseRegisterService) {
        this.houseRegisterService = houseRegisterService;

    }

    @GetMapping("/get")
    public ResponseEntity<HouseRegisterRespondDto> getHouseRegister(@RequestParam GetSpecificHouseRegisterDto dto) {
        HouseRegisterRespondDto houseRegisterRespondDto = houseRegisterService.getHouseRegisterDtoById(dto.registerId());
        return ResponseEntity.ok(houseRegisterRespondDto);
    }

    @GetMapping("")
    public ResponseEntity<List<HouseRegisterRespondDto>> getAllHouseRegister() {
        return ResponseEntity.ok(houseRegisterService.getAllHouseRegisterDto());
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveHouseRegister(@RequestBody HouseRegisterApproveDto dto,@AuthenticationPrincipal User user) {
        houseRegisterService.approveHouseRegister(dto.registerId(),user);
        return ResponseEntity.ok("Success");
    }
}
//    @PostMapping("/house-register")
//    public ResponseEntity<List<HouseRegisterRespondDto>> approveHouseRegister(@RequestBody HouseRegisterRequestDto dto){
//        return ResponseEntity.ok(houseRegisterService.getAllHouseRegisterDto());
//    }
//}
