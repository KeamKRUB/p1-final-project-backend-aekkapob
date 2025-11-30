package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.house.HouseRequest;
import ku.cs.project_SE.enums.house.HouseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HouseRequestRepository extends JpaRepository<HouseRequest, UUID> {
    List<HouseRequest>findHouseRequestsByRequester_UserId_AndStatusNot(UUID ownerId, HouseStatus status);
    List<HouseRequest> findByStatusOrStatus(HouseStatus status1, HouseStatus status2);

    List<HouseRequest> findByHouse_HouseId(Long houseHouseId);
}
