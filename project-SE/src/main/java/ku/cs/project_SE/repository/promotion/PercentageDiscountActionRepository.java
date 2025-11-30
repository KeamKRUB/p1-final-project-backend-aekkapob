package ku.cs.project_SE.repository.promotion;

import ku.cs.project_SE.entity.promotion.promotion_action.PercentageDiscountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PercentageDiscountActionRepository extends JpaRepository<PercentageDiscountAction, String> {
    Optional<PercentageDiscountAction> findByPromotion_PromotionId(String promotionId);
    void deleteByPromotion_PromotionId(String promotionId);
}
