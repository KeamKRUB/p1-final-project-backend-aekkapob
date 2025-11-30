package ku.cs.project_SE.model.filter.house_detail_renderer;

public interface RenderSpec {
    String getType();
    boolean isComposite();
    Object getValue();
}
