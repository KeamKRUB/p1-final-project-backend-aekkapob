package ku.cs.project_SE.dto.banner;

import ku.cs.project_SE.entity.banner.Banner;
import lombok.Data;

import java.util.List;

@Data
public class BannerResponse {
    private List<Banner> banner;
    private boolean success;
    private String status;
}
