// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.house.HouseRenovate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRenovateRepository extends JpaRepository<HouseRenovate, Long> {
    Optional<HouseRenovate> findByHouse_HouseId(Long houseId);
}
