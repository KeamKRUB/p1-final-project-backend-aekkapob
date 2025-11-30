package ku.cs.project_SE.mapper.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {

    private final String base; // เช่น "/images/"

    public UrlBuilder(@Value("${app.images.show-base:/images/}") String base) {
        this.base = base.endsWith("/") ? base : base + "/";
    }

    public String imageUrl(Long id) {
        return base + id;
    }
}
