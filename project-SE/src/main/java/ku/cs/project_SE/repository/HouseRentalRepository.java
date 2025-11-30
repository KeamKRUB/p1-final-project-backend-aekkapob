// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.house.HouseRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRentalRepository extends JpaRepository<HouseRental, Long> {
    Optional<HouseRental> findByHouse_HouseId(Long houseId);
}
