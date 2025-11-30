// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.enums.house.HouseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {
    Page<House> findByProject_ProjectNameContainingIgnoreCaseOrCaptionContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String projectName, String caption, String description, Pageable pageable
    );

    List<House> findHousesByOwner_UserId(UUID userId);

    @Query("SELECT DISTINCT h.houseType FROM House h WHERE h.houseType IS NOT NULL AND h.houseStatus = ku.cs.project_SE.enums.house.HouseStatus.APPROVED ORDER BY h.houseType")
    List<String> findDistinctHouseTypes();

    @Query("SELECT DISTINCT a.area FROM House h JOIN h.address a WHERE a.area IS NOT NULL AND h.houseStatus = ku.cs.project_SE.enums.house.HouseStatus.APPROVED AND a.area <> '' ORDER BY a.area")
    List<String> findDistinctAreas();

    @Query("SELECT DISTINCT a.province FROM House h JOIN h.address a WHERE a.province IS NOT NULL AND h.houseStatus = ku.cs.project_SE.enums.house.HouseStatus.APPROVED AND a.province <> '' ORDER BY a.province")
    List<String> findDistinctProvinces();
}
