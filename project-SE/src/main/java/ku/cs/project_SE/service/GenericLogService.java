package ku.cs.project_SE.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature; // Import เพิ่ม
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import ku.cs.project_SE.entity.log.EntityLog;
import ku.cs.project_SE.entity.log.LoggableEntity;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.ActionType;
import ku.cs.project_SE.repository.EntityLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GenericLogService {

    private final EntityLogRepository logRepository;
    private final ObjectMapper objectMapper; // นี่คือ objectMapper ที่ตั้งค่าแล้ว
    private static final Logger logger = LoggerFactory.getLogger(GenericLogService.class);


    public GenericLogService(EntityLogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;


        this.objectMapper = objectMapper.copy();


        this.objectMapper.registerModule(new Hibernate6Module());

        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public <T extends LoggableEntity> void logAction(T entity, ActionType action, User performedBy) {
        try {
            // ตอนนี้จะใช้ objectMapper ที่ตั้งค่าเรียบร้อยแล้ว
            String snapshotJson = objectMapper.writeValueAsString(entity);

            EntityLog log = EntityLog.builder()
                    .entityName(entity.getClass().getSimpleName())
                    .entityId(entity.getId())
                    .action(action)
                    .performedBy(performedBy)
                    .snapshot(snapshotJson)
                    .build();

            logRepository.save(log);

        } catch (Exception e) {
            // ควรใช้ Logger เพื่อบันทึกข้อผิดพลาดแทน e.printStackTrace()
            logger.error("Failed to log action for entity {}: {}",
                    (entity != null ? entity.getClass().getSimpleName() : "null"),
                    (entity != null ? entity.getId() : "null"),
                    e);
        }
    }
}
