package ku.cs.project_SE.model.filter.house_detail_renderer;

import java.util.List;

public class AllGalleryRenderSpec extends  LeafRenderSpec{
    public AllGalleryRenderSpec(List<HouseGalleryRenderSpec.Image> value) {
        super("all-gallery", value);
    }
}
