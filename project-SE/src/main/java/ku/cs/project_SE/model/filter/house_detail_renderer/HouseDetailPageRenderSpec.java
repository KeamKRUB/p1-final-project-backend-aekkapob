package ku.cs.project_SE.model.filter.house_detail_renderer;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailPageRenderSpec extends CompositeRenderSpec{
    public HouseDetailPageRenderSpec(RenderSpec heroSection, RenderSpec mainGallerySection, RenderSpec informationSection, RenderSpec allGallerySection, RenderSpec houseRegistration) {
        super("house-detail-page");
        this.addChild(heroSection);
        this.addChild(mainGallerySection);
        this.addChild(informationSection);
        this.addChild(houseRegistration);
        this.addChild(allGallerySection);
    }
}
