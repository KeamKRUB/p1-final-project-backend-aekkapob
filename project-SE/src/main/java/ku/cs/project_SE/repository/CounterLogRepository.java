package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.log.CounterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CounterLogRepository extends JpaRepository<CounterLog, UUID> {
    List<CounterLog> findByLogName(String logName);
    boolean existsByLogName(String logName);
    boolean existsById(UUID logId);

}