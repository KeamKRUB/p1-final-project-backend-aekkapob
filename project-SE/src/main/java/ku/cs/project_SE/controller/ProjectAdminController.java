// keam&tee
package ku.cs.project_SE.controller;


import ku.cs.project_SE.dto.project.ProjectCreateDto;
import ku.cs.project_SE.dto.project.ProjectResponseDto;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

@RestController
@RequestMapping("/admin/project")
public class ProjectAdminController {

    private final ProjectService service;

    public ProjectAdminController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> create(@RequestBody ProjectCreateDto dto) {
        Image image = null;
        if (dto.getImageId() != null) {
            image = new Image();
            image.setImageId(dto.getImageId());
        }
        return ResponseEntity.ok(service.create(dto, image));
    }

    @GetMapping("/delete/{projectName}")
    public String deleteProject(@PathVariable  String projectName) {
        String decode = URLDecoder.decode(projectName);
        service.delete(decode);
        return "200";
    }
}
