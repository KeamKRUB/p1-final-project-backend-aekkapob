package ku.cs.project_SE.dto.log;

import ku.cs.project_SE.entity.log.CounterLog;
import lombok.Data;

import java.util.List;

@Data
public class CounterLogResponse {
    private List<CounterLog> log;
    private boolean success;
    private String status;
}
