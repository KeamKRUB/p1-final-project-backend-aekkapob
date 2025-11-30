package ku.cs.project_SE.model.filter.house_detail_renderer;

import java.util.List;

public class HouseGalleryRenderSpec extends LeafRenderSpec {

    public record Image(
            Long imageId,
            String imageText
    ) {}

    private record HouseGallery(
            List<Image> images
    ) {}

    public HouseGalleryRenderSpec(List<Image> images) {
        super("house-gallery", new HouseGallery(images));
    }
}
