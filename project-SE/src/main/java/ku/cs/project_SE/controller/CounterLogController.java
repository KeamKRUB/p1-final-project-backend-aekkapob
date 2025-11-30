package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.log.CounterLogCreateDTO;
import ku.cs.project_SE.dto.log.CounterLogEditDTO;
import ku.cs.project_SE.dto.log.CounterLogEditResponseDTO;
import ku.cs.project_SE.dto.log.CounterLogResponse;
import ku.cs.project_SE.entity.log.CounterLog;
import ku.cs.project_SE.service.CounterLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/counter-log")
public class CounterLogController {
    private final CounterLogService counterLogService;

    public CounterLogController(CounterLogService counterLogService) {
        this.counterLogService = counterLogService;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST counter log API";
    }

    @GetMapping("/getAllLogs")
    public ResponseEntity<CounterLogResponse> getAllCounterLogs() {
        CounterLogResponse res = new CounterLogResponse();
        try{
            res.setLog(counterLogService.getAllCounterLogs());
            res.setStatus("Get all counter logs successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            System.err.println(e.getMessage());
            res.setLog(new ArrayList<>());
            res.setStatus("Get all counter logs failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CounterLogResponse> getCounterLogById(@PathVariable UUID id) {
        CounterLogResponse res = new CounterLogResponse();
        try {
            CounterLog counterLog = counterLogService.getById(id);
            if(counterLog == null){
                res.setLog(new ArrayList<>());
                res.setStatus("Counter log not found");
                res.setSuccess(false);
                return ResponseEntity.badRequest().body(res);
            }
            res.setLog(List.of(counterLog));
            res.setStatus("Get counter log by id successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);

        }catch (Exception e){
            System.err.println(e.getMessage());


            res.setLog(new ArrayList<>());
            res.setStatus("Get counter log by id failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<CounterLogResponse> getByName(@PathVariable  String name){
        CounterLogResponse res = new CounterLogResponse();
        try {
            CounterLog counterLog = counterLogService.getByName(name);
            if(counterLog == null){
                res.setLog(new ArrayList<>());
                res.setStatus("Counter log not found");
                res.setSuccess(false);
                return ResponseEntity.badRequest().body(res);
            }
            res.setLog(List.of(counterLog));
            res.setStatus("Get counter log by name successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);

        }catch (Exception e){
            System.err.println(e.getMessage());


            res.setLog(new ArrayList<>());
            res.setStatus("Get counter log by name failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CounterLogEditResponseDTO> create(@RequestBody CounterLogCreateDTO req){
        CounterLogEditResponseDTO res = new CounterLogEditResponseDTO();
        try {
            CounterLog counterLog = new CounterLog();
            counterLog.setLogName(req.getLogName());
            counterLog.setLogValue(req.getLogValue());
            counterLog = counterLogService.create(counterLog);
            if(counterLog == null){
                res.setStatus("Create counter log failed");
                res.setSuccess(false);
                return ResponseEntity.badRequest().body(res);
            }
            res.setStatus("Create counter log successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            System.err.println(e.getMessage());


            res.setStatus("Create counter log failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CounterLogEditResponseDTO> edit(@RequestBody CounterLogEditDTO req){
        CounterLogEditResponseDTO res = new CounterLogEditResponseDTO();
        try {
            CounterLog counterLog = new CounterLog();
            counterLog.setLogId(req.getLogId());
            counterLog.setLogName(req.getLogName());
            counterLog.setLogValue(req.getLogValue());

            if(!counterLogService.update(counterLog)){
                res.setStatus("Update counter log failed");
                res.setSuccess(false);
                return ResponseEntity.badRequest().body(res);
            }
            res.setStatus("Update counter log successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);

        }catch (Exception e){
            System.err.println(e.getMessage());


            res.setStatus("Update counter log failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<CounterLogEditResponseDTO> delete(@PathVariable UUID id){
        CounterLogEditResponseDTO res = new CounterLogEditResponseDTO();
        try {
            if(!counterLogService.delete(id)){
                res.setStatus("Delete counter log failed");
                res.setSuccess(false);
                return ResponseEntity.badRequest().body(res);
            }
            res.setStatus("Delete counter log successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());


            res.setStatus("Delete counter log failed");
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);

        }
    }

}
