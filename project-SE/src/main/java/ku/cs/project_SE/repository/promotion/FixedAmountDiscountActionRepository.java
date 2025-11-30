package ku.cs.project_SE.repository.promotion;

import ku.cs.project_SE.entity.promotion.promotion_action.FixedAmountDiscountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FixedAmountDiscountActionRepository extends JpaRepository<FixedAmountDiscountAction, String> {
    Optional<FixedAmountDiscountAction> findByPromotion_PromotionId(String promotionId);
    void deleteByPromotion_PromotionId(String promotionId);
}
