package ku.cs.project_SE.entity.promotion.promotion_action;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;

@Entity
@Table(name = "fixed_amount_discount_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedAmountDiscountAction implements PromotionAction {

    // ใช้ promotion_id เป็น Primary Key และเป็น FK ไปยัง promotions พร้อมกัน
    @Id
    @Column(name = "promotion_id", length = 64)
    private String promotionId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "discount_amount", nullable = false)
    @DecimalMin("0.0")
    private double discountAmount; // จำนวนเงินส่วนลดคงที่

    @Override
    public double calculate(House house) {
        if (house == null) return 0.0;
        double price = house.getPrice(); // ปรับชื่อให้ตรงกับโมเดลจริง
        if (price <= 0.0) return 0.0;
        // ส่วนลดคงที่แต่ไม่เกินราคาสินค้า และไม่ติดลบ
        return Math.max(0.0, Math.min(discountAmount, price));
    }
}
