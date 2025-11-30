// keam
package ku.cs.project_SE.dto.image;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateImageDto {
    @NotBlank
    private String path;
}