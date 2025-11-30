package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.VerifyUser.VerifyUser;
import ku.cs.project_SE.enums.VerifyUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VerifyUserRepository extends JpaRepository<VerifyUser, UUID> {
    List<VerifyUser> findByRequestStatus(VerifyUserStatus status);
    VerifyUser findByRequestStatusAndRequestId(VerifyUserStatus status,UUID requestId );
    VerifyUser findByRequestStatusAndRequester_UserId(VerifyUserStatus status,UUID requestId );
    VerifyUser findByCitizenIdAndRequestStatus(String citizenId,VerifyUserStatus status);
}
