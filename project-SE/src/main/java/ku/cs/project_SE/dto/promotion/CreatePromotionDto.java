// keam
package ku.cs.project_SE.dto.promotion;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreatePromotionDto {

    @NotBlank
    @Size(max = 100)
    private String promotionName;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private Boolean isActive;

    @NotNull
    @Min(0)
    private Integer usageLimit;

    @NotNull
    @Min(0)
    private Integer usedCount;
}
