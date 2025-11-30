package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.verifyUser.VerifyUserRequestDto;
import ku.cs.project_SE.entity.VerifyUser.VerifyUser;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.service.VerifyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/verify")
public class VeryfyUserController {
    VerifyUserService verifyUserService;

    public VeryfyUserController(VerifyUserService verifyUserService) {
        this.verifyUserService = verifyUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> verify(@RequestBody VerifyUserRequestDto dto, @AuthenticationPrincipal User user) {
        try {
            verifyUserService.createVerifyRequest(dto,user);
            return ResponseEntity.ok(true);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify-user")
    public ResponseEntity<?> isVerify(@AuthenticationPrincipal User user) {
        try {
            Boolean check = verifyUserService.checkIsVerifying(user);
            return ResponseEntity.ok(check);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
