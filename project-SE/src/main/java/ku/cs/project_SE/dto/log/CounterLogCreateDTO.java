package ku.cs.project_SE.dto.log;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CounterLogCreateDTO {
    private String logName;
    private BigDecimal logValue;
}
