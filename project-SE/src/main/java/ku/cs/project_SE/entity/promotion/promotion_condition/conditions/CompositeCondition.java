package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.promotion_condition.LogicalOperator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Entity
@Table(name = "composite_conditions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompositeCondition extends PromotionCondition {

    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false, length = 8)
    private LogicalOperator operator;

    @OneToMany(mappedBy = "parentComposite", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PromotionCondition> conditions = new ArrayList<>();

    public void addChild(PromotionCondition child) {
        if (conditions == null) conditions = new ArrayList<>();
        child.setParentComposite(this);
        child.setPromotion(this.getPromotion());
        conditions.add(child);
    }

    @Override
    public boolean evaluate(House house) {
        switch (operator) {
            case NOT:
                boolean child = conditions.stream().findFirst()
                        .map(c -> c.evaluate(house))
                        .orElse(false);
                return !child;
            case AND:
                for (PromotionCondition c : conditions) {
                    if (!c.evaluate(house)) return false;
                }
                return true;
            case OR:
                for (PromotionCondition c : conditions) {
                    if (c.evaluate(house)) return true;
                }
                return false;
            default:
                return false;
        }
    }
}
