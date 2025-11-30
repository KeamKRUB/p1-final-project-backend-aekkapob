package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.auth.LoginRequestDto;
import ku.cs.project_SE.dto.auth.RegisterRequestDto;
import ku.cs.project_SE.entity.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authManager,JWTService jwtService, UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Transactional
    public String login(LoginRequestDto request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.identifier(), request.password())
        );
        System.out.println("Authentication token: " + auth.getName());
        User user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        return jwtService.generateToken(user);
    }
}
