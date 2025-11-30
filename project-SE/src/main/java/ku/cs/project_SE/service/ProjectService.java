package ku.cs.project_SE.service;

import jakarta.transaction.Transactional;
import ku.cs.project_SE.dto.project.ProjectCreateDto;
import ku.cs.project_SE.dto.project.ProjectResponseDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.mapper.ProjectMapper;
import ku.cs.project_SE.mapper.helper.UrlBuilder;
import ku.cs.project_SE.repository.HouseRepository;
import ku.cs.project_SE.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final HouseRepository houseRepository;
    private final ProjectMapper mapper;
    private final UrlBuilder urlBuilder;

    public ProjectService(ProjectRepository projectRepository,
                          HouseRepository houseRepository,
                          ProjectMapper mapper,
                          UrlBuilder urlBuilder) {
        this.projectRepository = projectRepository;
        this.houseRepository = houseRepository;
        this.mapper = mapper;
        this.urlBuilder = urlBuilder;
    }

    public ProjectResponseDto create(ProjectCreateDto req, Image image) {
        Project entity = mapper.toEntity(req, image);
        Project saved = projectRepository.save(entity);
        return mapper.toDto(saved, urlBuilder);
    }

    public List<ProjectResponseDto> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(p -> mapper.toDto(p, urlBuilder))
                .toList();
    }

    public ProjectResponseDto getByName(String name) {
        Project p = projectRepository.findById(name).orElse(null);
        return p == null ? null : mapper.toDto(p, urlBuilder);
    }

    @Transactional
    public void delete(String projectName) {
        Project project = projectRepository.findById(projectName)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectName));

        Set<House> houses = project.getHouses();
        if (houses != null && !houses.isEmpty()) {
            for (House house : houses) {
                house.setProject(null);
            }
            houseRepository.deleteAll(houses);
        }

        projectRepository.delete(project);
    }

}
