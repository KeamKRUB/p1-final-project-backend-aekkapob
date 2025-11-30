// keam
package ku.cs.project_SE.mapper;

import ku.cs.project_SE.dto.promotion.CreatePromotionDto;
import ku.cs.project_SE.dto.promotion.PromotionResponseDto;
import ku.cs.project_SE.dto.promotion.UpdatePromotionDto;
import ku.cs.project_SE.entity.promotion.Promotion;
import org.mapstruct.*;
import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public interface PromotionMapper {

    @Mapping(target = "isActive", source = "active")
    PromotionResponseDto toDto(Promotion entity);

    @Mapping(target = "promotionId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Promotion toEntity(CreatePromotionDto req);

    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "active", source = "isActive")
    void applyUpdate(UpdatePromotionDto req, @MappingTarget Promotion entity);

    @AfterMapping
    default void setTimestampsOnCreate(CreatePromotionDto req, @MappingTarget Promotion entity) {
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
    }

    @AfterMapping
    default void touchUpdatedAt(UpdatePromotionDto req, @MappingTarget Promotion entity) {
        entity.setUpdatedAt(Instant.now());
    }

}
