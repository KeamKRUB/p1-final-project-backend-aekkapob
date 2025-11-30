// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
