package ku.cs.project_SE.dto.auth;

import lombok.Data;

@Data
public class RegisterResponse {
    private boolean success;
    private String status;
    private String token;

    public RegisterResponse(boolean success, String status,String token){
        this.success = success;
        this.status = status;
        this.token = token;
    }
}
