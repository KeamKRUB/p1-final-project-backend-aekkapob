package ku.cs.project_SE.model.filter.house_detail_renderer;

public class HouseHeroRenderSpec extends LeafRenderSpec {

    private record HouseHero(
            Long mainImageId,
            String houseName,
            String projectName,
            double priceValue,
            boolean renovate
    ){}

    public HouseHeroRenderSpec(Long mainImageId,String houseName, String projectName, double priceValue, boolean renovate) {
        super("house-hero", new HouseHero(mainImageId,houseName, projectName, priceValue, renovate));
    }
}
