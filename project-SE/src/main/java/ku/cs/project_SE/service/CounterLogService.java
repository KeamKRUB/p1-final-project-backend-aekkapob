package ku.cs.project_SE.service;

import ku.cs.project_SE.entity.log.CounterLog;
import ku.cs.project_SE.repository.CounterLogRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CounterLogService {
    private final CounterLogRepository counterLogRepository;

    public CounterLogService(CounterLogRepository counterLogRepository) {
        this.counterLogRepository = counterLogRepository;
    }

    public List<CounterLog> getAllCounterLogs() {
        try {
            List<CounterLog> counterLogs = counterLogRepository.findAll();
            if (counterLogs == null || counterLogs.isEmpty()) return new ArrayList<>();
            return counterLogs;
        } catch (Exception e) {
            System.err.println(e.getMessage());


            return new ArrayList<>();
        }
    }
    public CounterLog getById(UUID id){
        try {
            return counterLogRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.err.println(e.getMessage());


            return null;
        }
    }

    public CounterLog getByName(String name){
        try {
            List<CounterLog> counterLogs = counterLogRepository.findByLogName(name);
            if(counterLogs.isEmpty()) return null;
            return counterLogs.get(0);

        }catch (Exception e){
            System.err.println(e.getMessage());


            return null;
        }
    }

    public CounterLog create(CounterLog counterLog){
        try{
            if(counterLog == null) throw new Exception("CounterLog can't be null");
            if(counterLog.getLogName() == null) throw new Exception("CounterLog name can't be null");
            if(counterLog.getLogName().isEmpty()) throw new Exception("CounterLog name can't be empty");
            if(counterLog.getLogValue() == null) counterLog.setLogValue(new BigDecimal(0));
            if(counterLog.getLogId() != null && counterLogRepository.existsById(counterLog.getLogId()) || counterLogRepository.existsByLogName(counterLog.getLogName()))  throw  new Exception("CounterLog already exists");

            return counterLogRepository.save(counterLog);
        }catch (Exception e){
            System.err.println(e.getMessage());


            return null;
        }

    }

    public Boolean update(CounterLog counterLog){
        try {
            if(counterLog == null) throw new Exception("CounterLog can't be null");
            if(counterLog.getLogId() == null) throw new Exception("CounterLog id can't be null");

            Optional<CounterLog> optionalLog = counterLogRepository.findById(counterLog.getLogId());
            if(!optionalLog.isPresent()) throw new Exception("CounterLog not found");
            CounterLog existingLog = optionalLog.get();

            if(counterLog.getLogName() != null){
                if(!existingLog.getLogName().equals(counterLog.getLogName())
                && (counterLogRepository.existsByLogName(counterLog.getLogName()))) throw new Exception("CounterLog name already exists");
                else existingLog.setLogName(counterLog.getLogName());
            }

            if(counterLog.getLogValue() != null) existingLog.setLogValue(counterLog.getLogValue());

            counterLogRepository.save(existingLog);
            return true;

        }catch (Exception e){
            System.err.println(e.getMessage());


            return false;
        }
    }

    public Boolean delete(UUID id){
        try {
            counterLogRepository.deleteById(id);
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());


            return false;
        }
    }
}
