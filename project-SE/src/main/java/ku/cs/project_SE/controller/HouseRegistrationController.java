package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.house_register.HouseRegisterRequestDto;
import ku.cs.project_SE.dto.house_register.HouseRegisterRespondDto;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.HouseRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Tee
@RestController
@RequestMapping("/house-visit")
public class HouseRegistrationController {

    private final HouseRegisterService houseRegisterService;

    HouseRegistrationController(HouseRegisterService houseRegisterService) {
        this.houseRegisterService = houseRegisterService;

    }

    @PostMapping("/register")
    public ResponseEntity<String> houseRegister(@RequestBody HouseRegisterRequestDto houseRegisterDto, @AuthenticationPrincipal User user) {
        HouseRegister houseRegister = houseRegisterService.createHouseRegister(houseRegisterDto.houseId(),user.getUserId());
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/register")
    public ResponseEntity<List<HouseRegisterRespondDto>>getRegister(@AuthenticationPrincipal User user) {
        List<HouseRegisterRespondDto> houseRegister = houseRegisterService.getHouseRegisterByUserId(user.getUserId());
        return ResponseEntity.ok(houseRegister);
    }


}
