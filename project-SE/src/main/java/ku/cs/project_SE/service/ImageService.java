//keam
package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.image.ImageDto;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.mapper.ImageMapper;
import ku.cs.project_SE.repository.ImageRepository;
import ku.cs.project_SE.service.S3.S3PresignService;
import ku.cs.project_SE.service.S3.S3StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository repository;
    private final ImageMapper mapper;
    private final S3StorageService s3;
    private final S3PresignService presign;

    public ImageService(ImageRepository repository,
                        ImageMapper mapper,
                        S3StorageService s3,
                        S3PresignService presign) {
        this.repository = repository;
        this.mapper = mapper;
        this.s3 = s3;
        this.presign = presign;
    }

    public Image getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Image not found: " + id));
    }

    public ImageDto getImage(Long id) {
        var img = getById(id);
        var dto = mapper.toDto(img);
        dto.setUrl(presign.presignGetUrl(img.getImagePath(), Duration.ofMinutes(10)));
        return dto;
    }

    @Transactional
    public ImageDto upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Empty file");
        }
        var uploaded = s3.upload(file, "");
        Image saved = repository.save(new Image(null, uploaded.key()));
        var dto = mapper.toDto(saved);
        dto.setUrl(presign.presignGetUrl(saved.getImagePath(), Duration.ofMinutes(10)));
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        var img = getById(id);
        s3.delete(img.getImagePath());
        repository.delete(img);
    }
}
