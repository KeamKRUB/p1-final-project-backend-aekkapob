package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.banner.Banner;
import ku.cs.project_SE.entity.banner.BannerType;
import ku.cs.project_SE.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Tee
public interface BannerRepository extends JpaRepository<Banner, UUID> {
    List<Banner> findByBannerType(BannerType bannerType);
}