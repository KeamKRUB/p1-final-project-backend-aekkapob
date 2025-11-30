package ku.cs.project_SE.model.filter.house_detail_renderer;

public class KeyValueRenderSpec extends LeafRenderSpec {

    private record KeyValue(
            String key,
            String value
    ) {}

    protected KeyValueRenderSpec(String key, String value) {
        super("key-value", new KeyValue(key, value));
    }
}