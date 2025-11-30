package ku.cs.project_SE.model.filter.house_detail_renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CompositeRenderSpec implements RenderSpec {
    private final String type;
    private final List<RenderSpec> children;

    protected CompositeRenderSpec(String type, List<RenderSpec> children) {
        this.type = type;
        this.children = new ArrayList<>(children);
    }

    protected CompositeRenderSpec(String type, RenderSpec... children) {
        this(type, Arrays.asList(children));
    }

    protected CompositeRenderSpec(String type){
        this(type, new ArrayList<>());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    @Override
    public List<RenderSpec> getValue() {
        return new ArrayList<>(children);
    }

    public void addChild(RenderSpec child) {
        children.add(child);
    }
}