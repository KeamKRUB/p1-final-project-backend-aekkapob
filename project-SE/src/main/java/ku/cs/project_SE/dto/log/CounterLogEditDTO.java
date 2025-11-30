package ku.cs.project_SE.dto.log;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CounterLogEditDTO {
    private UUID logId;
    private String logName;
    private BigDecimal logValue;
}
