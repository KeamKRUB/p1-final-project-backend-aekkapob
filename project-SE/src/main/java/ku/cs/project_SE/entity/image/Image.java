// keam
package ku.cs.project_SE.entity.image;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "image_path", nullable = false, length = 500)
    private String imagePath;
}
