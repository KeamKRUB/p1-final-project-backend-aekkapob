// keam
package ku.cs.project_SE.entity.promotion;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import ku.cs.project_SE.entity.promotion.promotion_action.FixedAmountDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_action.PercentageDiscountAction;
import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "promotion_id", length = 64)
    private String promotionId;


    @NotBlank
    @Column(nullable = false)
    private String promotionName;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private boolean isActive = true;

    @Min(0)
    @Column(nullable = false)
    private int usageLimit = 0;

    @Min(0)
    @Column(nullable = false)
    private int usedCount = 0;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "promotion",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<CompositeCondition> conditions = new ArrayList<>();

    @OneToOne(mappedBy = "promotion",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private PercentageDiscountAction percentageAction;

    @OneToOne(mappedBy = "promotion",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private FixedAmountDiscountAction fixedAction;

    @OneToOne(mappedBy = "promotion",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)

    public void setPercentageAction(PercentageDiscountAction a) {
        this.percentageAction = a;
        if (a != null) { a.setPromotion(this); a.setPromotionId(this.promotionId); }
        this.fixedAction = null;
    }
    public void setFixedAction(FixedAmountDiscountAction a) {
        this.fixedAction = a;
        if (a != null) { a.setPromotion(this); a.setPromotionId(this.promotionId); }
        this.percentageAction = null;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (this.createdAt == null) this.createdAt = now;
        if (this.updatedAt == null) this.updatedAt = now;
        if (this.createdBy == null || this.createdBy.isBlank()) {
            this.createdBy = "system";
        }

        if (this.usedCount < 0) this.usedCount = 0;
        if (this.usageLimit < 0) this.usageLimit = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
