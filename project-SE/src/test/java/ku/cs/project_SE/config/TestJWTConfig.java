package ku.cs.project_SE.config;

import ku.cs.project_SE.security.JWTConfig;
import ku.cs.project_SE.security.JwtUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestJWTConfig {

    @Bean
    public JwtUtil jwtUtil() {
        JwtUtil mockJwtUtil = mock(JwtUtil.class);
        // mock ให้ getSecret() คืนค่า test-secret
        when(mockJwtUtil.getSecret()).thenReturn("test-secret");
        return mockJwtUtil;
    }

    @Bean
    public JWTConfig jwtConfig(JwtUtil jwtUtil) {
        // constructor injection
        return new JWTConfig(jwtUtil);
    }
}
