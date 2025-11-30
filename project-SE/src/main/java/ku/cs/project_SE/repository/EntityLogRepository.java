package ku.cs.project_SE.repository;


import ku.cs.project_SE.entity.log.EntityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntityLogRepository  extends JpaRepository<EntityLog, UUID> {

}
