package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.promotion.Promotion;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "date_range_conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRangeCondition extends PromotionCondition {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private java.util.Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private java.util.Date endDate;

    @Override
    public boolean evaluate(House house) {
        if (startDate == null || endDate == null) return false;
        java.util.Date now = new java.util.Date();
        return !now.before(startDate) && !now.after(endDate);
    }
}
