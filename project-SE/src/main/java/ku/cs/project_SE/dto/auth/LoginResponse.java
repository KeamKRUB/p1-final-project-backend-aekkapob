package ku.cs.project_SE.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean success;
    private String status;
    private String token;
    public LoginResponse(){}
    public LoginResponse(boolean success,String status,String token) {
        this.success = success;
        this.status = status;
        this.token = token;
    }

}
