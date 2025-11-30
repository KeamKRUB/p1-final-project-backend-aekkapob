// keam
package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.project.ProjectCreateDto;
import ku.cs.project_SE.dto.project.ProjectResponseDto;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.mapper.helper.UrlBuilder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "imageId",
            expression = "java(project.getImage() != null ? project.getImage().getImageId() : null)")
    @Mapping(target = "imageUrl",
            expression = "java(project.getImage() != null ? urlBuilder.imageUrl(project.getImage().getImageId()) : null)")
    ProjectResponseDto toDto(Project project, @Context UrlBuilder urlBuilder);

    @Mapping(target = "image", source = "image")
    Project toEntity(ProjectCreateDto dto, Image image);
}
