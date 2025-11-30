package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.banner.*;
import ku.cs.project_SE.dto.image.ImageDto;
import ku.cs.project_SE.entity.banner.Banner;
import ku.cs.project_SE.entity.banner.BannerType;
import ku.cs.project_SE.service.BannerService;
import ku.cs.project_SE.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;
    private final ImageService imageService;

    @Autowired
    public BannerController(BannerService bannerService, ImageService imageService) {
        this.bannerService = bannerService;
        this.imageService = imageService;
    }
    @GetMapping("/test")
    public String getHome() {
        return "Welcome to Banner API";
    }

    @GetMapping("/getAllBanners")
    public ResponseEntity<BannerResponse> getAllBanners() {
        BannerResponse res = new BannerResponse();
        try {
            List<Banner> banners = bannerService.getAllBanners();
            res.setBanner(banners);
            res.setStatus("Get all banners successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.setBanner(new ArrayList<>());
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<BannerResponse> getBannersByType(@PathVariable UUID id) {
        BannerResponse res = new BannerResponse();
        try {
            List<Banner> banners = bannerService.getBannersById(id);
            res.setBanner(banners);
            res.setStatus("Get banners by type successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            res.setBanner(new ArrayList<>());
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }
    @GetMapping("/getByType/{type}")
    public ResponseEntity<BannerResponse> getBannersByType(@PathVariable BannerType type) {
        BannerResponse res = new BannerResponse();
        try {
            List<Banner> banners = bannerService.getBannersByType(type);
            res.setBanner(banners);
            res.setStatus("Get banners by type successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            res.setBanner(new ArrayList<>());
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<BannerEditResponseDTO> deleteBanner(@PathVariable UUID id) {
        BannerEditResponseDTO res = new BannerEditResponseDTO();
        try{
            List<Banner> existingBanners = bannerService.getBannersById(id);
            if(existingBanners.isEmpty()) throw new Exception("Banner not found");
            Banner existingBanner = existingBanners.get(0);
            if(existingBanner.getBannerPath() != null && !existingBanner.getBannerPath().equals("EMPTY")) {
                try {
                    String imageId = existingBanner.getBannerPath();
                    imageService.delete(Long.parseLong(imageId));
                } catch (Exception e) {
                    System.err.println(e.getMessage());


                    throw new Exception("Cannot delete old image from Banner");
                }
            }
            bannerService.deleteBanner(id);
            res.setStatus("Delete banner successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEditResponseDTO> createBanner(@ModelAttribute BannerEditDTO req) {
        BannerEditResponseDTO res = new BannerEditResponseDTO();
        try{
            if(req.getBanner() == null) throw new Exception("Cannot create null banner");
            if(req.getBanner().getBannerName() == null || req.getBanner().getBannerName().isEmpty()) throw new Exception("Cannot create banner without name");
            if(req.getBanner().getBannerType() == null) throw new Exception("Cannot create banner without type");

            req.getBanner().setBannerPath("EMPTY");
            System.err.println(req.getBanner().toString());

            if(req.getImage() != null && !req.getImage().isEmpty()) {
                try {
                    ImageDto imageDto = imageService.upload(req.getImage());
                    req.getBanner().setBannerPath(imageDto.getId().toString());
                }catch (Exception e) {
                    System.err.println(e.getMessage());


                    req.getBanner().setBannerPath("EMPTY");
                }
            }

            if(bannerService.createBanner(req.getBanner()) == null){
                throw new Exception("Cannot create banner");
            }
            res.setStatus("Create banner successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerEditResponseDTO> updateBanner(@ModelAttribute BannerEditDTO req) {
        BannerEditResponseDTO res = new BannerEditResponseDTO();
        try{
            if(req.getBannerId() == null) throw new Exception("Cannot update null banner");
            List<Banner> existingBanners = bannerService.getBannersById(req.getBannerId());
            if(existingBanners.isEmpty()) throw new Exception("Banner not found");
            Banner reqBanner = req.getBanner();
            Banner existingBanner = existingBanners.get(0);

            if(reqBanner != null && reqBanner.getBannerName() != null)  existingBanner.setBannerName(reqBanner.getBannerName());
            if(reqBanner != null && reqBanner.getBannerType() != null)  existingBanner.setBannerType(reqBanner.getBannerType());
            if(reqBanner != null && reqBanner.getBannerLink() != null)  existingBanner.setBannerLink(reqBanner.getBannerLink());

            if(req.getImage() != null && !req.getImage().isEmpty()) {
                if(existingBanner.getBannerPath() != null && !existingBanner.getBannerPath().equals("EMPTY")) {
                    try {
                        String imageId  = existingBanner.getBannerPath();
                        imageService.delete(Long.parseLong(imageId));
                        existingBanner.setBannerPath("EMPTY");
                        bannerService.updateBanner(existingBanner);
                    } catch (Exception e) {
                        throw new Exception("Cannot delete old image from Banner");
                    }

                }
                ImageDto imageDto = imageService.upload(req.getImage());
                existingBanner.setBannerPath(imageDto.getId().toString());
            }

            if(bannerService.updateBanner(existingBanner) == null){
                throw new Exception("Cannot update banner");
            }
            res.setStatus("Update banner successfully");
            res.setSuccess(true);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.setStatus(e.getMessage());
            res.setSuccess(false);
            return ResponseEntity.badRequest().body(res);
        }
    }
}
