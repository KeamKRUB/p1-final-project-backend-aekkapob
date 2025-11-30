package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;
import ku.cs.project_SE.entity.house.House;

@Entity
@Table(name = "promotion_conditions")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public abstract class PromotionCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_id", nullable = false)
    protected Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_composite_id")
    protected CompositeCondition parentComposite;

    public abstract boolean evaluate(House house);
}
