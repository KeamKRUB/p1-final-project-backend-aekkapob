package ku.cs.project_SE.dto.banner;

import ku.cs.project_SE.entity.banner.Banner;
import ku.cs.project_SE.entity.banner.BannerType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class BannerEditDTO {
    private UUID bannerId;
    private Banner banner;
    private MultipartFile image;
}
