package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.auth.LoginRequestDto;
import ku.cs.project_SE.dto.auth.LoginResponse;
import ku.cs.project_SE.dto.auth.RegisterRequestDto;
import ku.cs.project_SE.dto.auth.RegisterResponse;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.exception.UserExistsException;
import ku.cs.project_SE.service.AuthService;
import ku.cs.project_SE.service.JWTService;
import ku.cs.project_SE.service.UserService;
import ku.cs.project_SE.service.validation.PasswordValidationContext;
import ku.cs.project_SE.service.validation.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

//Tee
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final JWTService jwtService;
    private final UserService userService;
    private final AuthService authService;
    private final PasswordValidator passwordValidator;

    @Autowired
    public AuthController(JWTService jwtService, UserService userService,AuthService authService, PasswordValidator passwordValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authService = authService;
        this.passwordValidator = passwordValidator;
    }


    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequestDto dto){
        PasswordValidationContext ctx = new PasswordValidationContext(
                dto.email(),
                dto.firstName(),
                dto.lastName()
        );

        String error = passwordValidator.getErrorMessage(dto.password(), ctx);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        try{
            User user = userService.createUser(dto);
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new RegisterResponse(true,"Register Successfully",token));
        }catch (UserExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RegisterResponse(false, e.getMessage(), null));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegisterResponse(false, "Something went wrong", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto request) {
        try {
            String token = authService.login(request);

            return ResponseEntity.ok(new LoginResponse(true,"Login Successfully",token));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Invalid credentials", ""));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }


}
