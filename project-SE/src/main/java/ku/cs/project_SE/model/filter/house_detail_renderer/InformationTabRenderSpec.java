package ku.cs.project_SE.model.filter.house_detail_renderer;

import java.util.List;

public class InformationTabRenderSpec extends LeafRenderSpec {

    public record InformationContent(
            String title,
            String text,
            String emoji
    ) {}

    public record InformationPage(
            List<InformationContent> contents
    ) {}

    public record InformationTab(
            List<InformationPage> tab
    ) {}

    public InformationTabRenderSpec(List<InformationPage> tab) {
        super("information-tab", new InformationTab(tab));
    }
}