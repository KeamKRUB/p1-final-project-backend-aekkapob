package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.promotion.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, String > {

    @Query("""
        select p
        from Promotion p
        where (:q is null or :q = '')
           or lower(p.promotionName) like lower(concat('%', :q, '%'))
           or lower(p.description) like lower(concat('%', :q, '%'))
           or str(p.promotionId) like concat('%', :q, '%')
    """)
    Page<Promotion> search(@Param("q") String q, Pageable pageable);

    @Query("""
        select p
        from Promotion p
        where
          ( :q is null or :q = ''
            or lower(p.promotionName) like lower(concat('%', :q, '%'))
            or lower(p.description)   like lower(concat('%', :q, '%'))
            or str(p.promotionId)     like concat('%', :q, '%')
          )
          and ( :active is null or p.isActive = :active )
    """)
    Page<Promotion> searchWithFilter(@Param("q") String q,
                                     @Param("active") Boolean active,
                                     Pageable pageable);

    Optional<Promotion> findByPromotionName(String promotionName);
}
