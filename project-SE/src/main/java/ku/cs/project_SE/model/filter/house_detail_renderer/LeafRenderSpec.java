package ku.cs.project_SE.model.filter.house_detail_renderer;

public abstract class LeafRenderSpec implements RenderSpec {
    private final String type;
    private final Object value;

    protected LeafRenderSpec(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
