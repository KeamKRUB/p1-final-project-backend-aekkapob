// keam&tee
package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.project.ProjectResponseDto;
import ku.cs.project_SE.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
