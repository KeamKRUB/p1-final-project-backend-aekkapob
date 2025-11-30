package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.house_register.HouseRegisterId;
import ku.cs.project_SE.enums.HouseRegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HouseRegisterRepository extends JpaRepository<HouseRegister, UUID> {
    List<HouseRegister> findHouseRegistersByUser_UserIdOrderByDateAsc(UUID user_Id);
    List<HouseRegister> findHouseRegistersByRegisterStatus(HouseRegisterStatus status);
    HouseRegister findHouseRegistersByRegisterId(UUID id);
    List<HouseRegister> findHouseRegisterByHouse_HouseIdAndUser_UserId_AndRegisterStatus(long houseId, UUID userId,HouseRegisterStatus status);
}
