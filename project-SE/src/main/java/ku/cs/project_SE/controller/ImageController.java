// keam
package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.image.ImageDto;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> upload(@RequestPart("file") MultipartFile file) {
        System.out.println("Received file: " + file.getOriginalFilename());
        ImageDto dto = service.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping(path = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto[]> uploadMany(@RequestPart("files") MultipartFile[] files) {
        ImageDto[] result = Arrays.stream(files)
                .map(service::upload)
                .toArray(ImageDto[]::new);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> showImage(@PathVariable Long id) {
        var dto = service.getImage(id);
        return ResponseEntity.status(HttpStatus.FOUND) // 302
                .header(HttpHeaders.LOCATION, dto.getUrl())
                .build();
    }

    @GetMapping("/{id}/meta")
    public ResponseEntity<ImageDto> getImageMeta(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getImage(id));
    }

}
