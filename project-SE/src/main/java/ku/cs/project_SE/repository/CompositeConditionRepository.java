// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.promotion.promotion_condition.conditions.CompositeCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositeConditionRepository extends JpaRepository<CompositeCondition, Long> {
    List<CompositeCondition> findByPromotion_PromotionId(String promotionId);
}