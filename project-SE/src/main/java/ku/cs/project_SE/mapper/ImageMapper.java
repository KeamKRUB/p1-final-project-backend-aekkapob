// keam
package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.image.ImageDto;
import ku.cs.project_SE.dto.image.CreateImageDto;
import ku.cs.project_SE.entity.image.Image;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "id",   source = "imageId")
    @Mapping(target = "path", source = "imagePath")
    @Mapping(target = "url",  ignore = true)
    ImageDto toDto(Image image);

    @Mapping(target = "imageId", ignore = true)
    @Mapping(target = "imagePath", source = "path")
    Image toEntity(CreateImageDto dto);
}
