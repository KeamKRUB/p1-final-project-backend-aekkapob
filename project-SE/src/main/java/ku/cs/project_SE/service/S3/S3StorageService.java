// keam
package ku.cs.project_SE.service.S3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
public class S3StorageService {

    private final S3Client s3;

    @Value("${app.aws.s3.bucket}")
    private String bucket;

    @Value("${app.aws.s3.basePrefix:}")
    private String basePrefix;

    public S3StorageService(S3Client s3) {
        this.s3 = s3;
    }

    public UploadedObject upload(MultipartFile file, String folder) {
        try {
            String ext = guessExt(file.getOriginalFilename());
            String key = buildKey(folder, ext);

            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(resolveContentType(file))
                    .contentLength(file.getSize())
                    //.acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3.putObject(req, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return new UploadedObject(bucket, key);
        } catch (IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }

    public void delete(String key) {
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
    }

    private String buildKey(String folder, String ext) {
        String cleanFolder = (folder == null ? "" : folder.trim().replaceAll("(^/)|(/$)", ""));
        String prefix = (basePrefix == null ? "" : basePrefix) + (cleanFolder.isEmpty() ? "" : cleanFolder + "/");
        return prefix + Instant.now().toEpochMilli() + "-" + UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
    }

    private String guessExt(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot >= 0 && dot < filename.length() - 1) ? filename.substring(dot + 1) : "";
    }

    private String resolveContentType(MultipartFile f) {
        String ct = f.getContentType();
        return (ct == null || ct.isBlank()) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : ct;
    }

    public record UploadedObject(String bucket, String key) {
        public String s3Uri() { return "s3://" + bucket + "/" + key; }
        public String key() { return key; }
        public String bucket() { return bucket; }
    }
}
