// keam&tee
package ku.cs.project_SE.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectCreateDto {
    @NotBlank
    private String projectName;

    private String projectDescription;

    private Long imageId;
}
