package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;

@Entity
@Table(name = "product_category_conditions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryCondition extends PromotionCondition {

    @Column(name = "category_id", nullable = false, length = 64)
    private String categoryId;

    @Override
    public boolean evaluate(House house) {
        // TODO: เช็ก category ของบ้านจาก domain จริง
        return true;
    }
}
