// keam
package ku.cs.project_SE.entity.promotion.promotion_condition.conditions;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house_in_conditions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HouseInCondition extends PromotionCondition {

    @ElementCollection
    @CollectionTable(name = "house_in_condition_houses",
            joinColumns = @JoinColumn(name = "condition_id"))
    @Column(name = "house_id")
    private List<Long> houseIds = new ArrayList<>();

    @Override
    public boolean evaluate(House house) {
        return house != null && houseIds.contains(house.getHouseId());}
}
