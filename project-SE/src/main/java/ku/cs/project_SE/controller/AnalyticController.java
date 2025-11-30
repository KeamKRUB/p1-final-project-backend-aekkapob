package ku.cs.project_SE.controller;


import ku.cs.project_SE.dto.analytic.AnalyticResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytic")
public class AnalyticController {

    @GetMapping("/event")
    @ResponseBody
    public AnalyticResponseDTO addEvent(@RequestParam(required = true) String eventName, @RequestParam(required = true) String eventText){
        return new AnalyticResponseDTO(true);
    }
}
