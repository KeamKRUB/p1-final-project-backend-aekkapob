package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;

@Entity
@Table(name = "minimum_price_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinimumPriceCondition extends PromotionCondition {

    @Column(name = "minimum_price", nullable = false)
    private double minimumPrice;

    @Override
    public boolean evaluate(House house) {
        if (house == null) return false;
        return house.getPrice() >= minimumPrice;
    }
}
