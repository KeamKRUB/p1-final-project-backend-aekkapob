package ku.cs.project_SE.model.filter.house_detail_renderer;

public class HouseRegistrationRenderSpec extends LeafRenderSpec {

    public record HouseRegistration(
            long houseId,
            boolean registeredForVisit
    ) {}

    public HouseRegistrationRenderSpec(long houseId, boolean registeredForVisit) {
        super("house-registration", new HouseRegistration(houseId, registeredForVisit));
    }
}
