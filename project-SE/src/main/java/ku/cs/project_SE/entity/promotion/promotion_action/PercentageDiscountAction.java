package ku.cs.project_SE.entity.promotion.promotion_action;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;

@Entity
@Table(name = "percentage_discount_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PercentageDiscountAction implements PromotionAction {

    @Id
    @Column(name = "promotion_id", length = 64)
    private String promotionId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "percentage", nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double percentage;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Override
    public double calculate(House house) {
        if (house == null) return 0.0;
        double price = house.getPrice();
        if (price <= 0) return 0.0;
        double discount = price * (percentage / 100.0);
        if (maxDiscount != null && maxDiscount > 0) {
            discount = Math.min(discount, maxDiscount);
        }
        return Math.max(0.0, Math.min(discount, price));
    }
}

