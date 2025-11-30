package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.banner.*;
import ku.cs.project_SE.entity.banner.Banner;
import ku.cs.project_SE.entity.banner.BannerType;
import ku.cs.project_SE.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getAllBanners() {
        try{
            List<Banner> banners = bannerRepository.findAll();
            return banners;
        } catch (Exception e){
            System.err.println(e.getMessage());


            return new ArrayList<>();
        }
    }

    public List<Banner> getBannersById(UUID bannerId) {
        try {
            Optional<Banner> banner = bannerRepository.findById(bannerId);
            return banner.map(List::of).orElseGet(ArrayList::new);
        } catch (Exception e) {
            System.err.println(e.getMessage());


            return new ArrayList<>();
        }
    }

    public List<Banner> getBannersByType(BannerType bannerType) {
        try {
            List<Banner> banners = bannerRepository.findByBannerType(bannerType);
            return banners;
        } catch (Exception e) {
            System.err.println(e.getMessage());


            return new ArrayList<>();
        }
    }

    public Boolean deleteBanner(UUID bannerId) {
        try {
            bannerRepository.deleteById(bannerId);
            return true;
        }catch (Exception e) {
            System.err.println(e.getMessage());


            return false;
        }
    }

    public Banner createBanner(Banner banner){
        try{
            // Pure CREATE: ensure JPA treats this as new; ID must be null so @GeneratedValue runs
            banner.setBannerId(null);
            return bannerRepository.save(banner);
        } catch (Exception e){
            System.err.println(e.getMessage());


            return null;
        }
    }

    public Banner updateBanner(Banner banner){
        try{
            if (banner.getBannerId() == null) return null; // must have ID to update
            boolean isExist = bannerRepository.existsById(banner.getBannerId());
            if(!isExist) return null;
            return bannerRepository.save(banner);
        } catch (Exception e){
            System.err.println(e.getMessage());


            return null;
        }
    }
}