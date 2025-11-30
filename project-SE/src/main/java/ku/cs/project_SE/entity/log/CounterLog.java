package ku.cs.project_SE.entity.log;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "counter_logs")
public class CounterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "log_id", updatable = false, nullable = false)
    private UUID logId;
    @Column(name = "log_name", nullable = false)
    private String logName;
    @Column(name = "log_value", nullable = false)
    private BigDecimal logValue;

}
