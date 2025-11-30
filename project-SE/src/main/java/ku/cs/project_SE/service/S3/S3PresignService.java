// keam
package ku.cs.project_SE.service.S3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Service
public class S3PresignService {

    private final S3Presigner presigner;

    @Value("${app.aws.s3.bucket}")
    private String bucket;

    public S3PresignService(S3Presigner presigner) {
        this.presigner = presigner;
    }

    public String presignGetUrl(String key, Duration ttl) {
        var get = GetObjectRequest.builder().bucket(bucket).key(key).build();
        var req = GetObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .getObjectRequest(get)
                .build();
        URL url = presigner.presignGetObject(req).url();
        return url.toString();
    }
}
