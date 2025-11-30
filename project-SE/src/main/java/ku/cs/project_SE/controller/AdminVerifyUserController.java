package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.admin.AdminVerifyUserDto;
import ku.cs.project_SE.dto.verifyUser.VerifyUserResponseDto;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.VerifyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/investor-management")
public class AdminVerifyUserController {
    private final VerifyUserService verifyUserService;

    public AdminVerifyUserController(VerifyUserService verifyUserService) {
        this.verifyUserService = verifyUserService;
    }

    @GetMapping("/get-verification")
    public ResponseEntity<List<VerifyUserResponseDto>> getVerifyUser(@AuthenticationPrincipal User user) {
        System.out.println("dasdasd"+user.getEmail());
        List<VerifyUserResponseDto> verifyUserResponseDtos = verifyUserService.getVerifyUser();
        return ResponseEntity.ok(verifyUserResponseDtos);
    }

    @GetMapping("/get-verification/{requestId}")
    public ResponseEntity<VerifyUserResponseDto> getVerifyUserByRequestId(@PathVariable String requestId)  {
        VerifyUserResponseDto verifyUserResponseDtos = verifyUserService.getVerifyUserByRequestId(UUID.fromString(requestId));
        return ResponseEntity.ok(verifyUserResponseDtos);
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approve(@RequestBody AdminVerifyUserDto dto, @AuthenticationPrincipal User user) {
        System.out.println("Approving user " + user.getEmail());
        verifyUserService.approve(dto.requestId(),user);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> reject(@RequestBody AdminVerifyUserDto dto, @AuthenticationPrincipal User user) {
        verifyUserService.reject(dto.requestId(),user,dto.reason());
        return ResponseEntity.ok("Success");
    }
}
