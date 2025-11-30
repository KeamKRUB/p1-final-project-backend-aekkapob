package ku.cs.project_SE.entity.banner;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "banners")
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "banner_id", updatable = false, nullable = false)
    private UUID bannerId;
    @Column(name = "banner_name", nullable = false)
    private String bannerName;
    @Column(name = "banner_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BannerType bannerType;
    @Column(name = "banner_path", nullable = false)
    private String bannerPath;
    @Column(name = "banner_link", nullable = false)
    private String bannerLink;
}
