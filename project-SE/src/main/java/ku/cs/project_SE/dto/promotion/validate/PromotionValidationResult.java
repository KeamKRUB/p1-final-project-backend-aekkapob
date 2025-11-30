package ku.cs.project_SE.dto.promotion.validate;

import ku.cs.project_SE.dto.promotion.action.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionValidationResult {
    private boolean valid;
    private String message;
    private ActionDto action;
    private double finalPrice;
}
