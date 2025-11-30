package ku.cs.project_SE;

import ku.cs.project_SE.config.TestMailConfig;
// import ku.cs.project_SE.config.TestJWTConfig; // อาจจะไม่ต้องใช้แล้ว
import ku.cs.project_SE.service.JWTService; // Import service ที่จะ Mock
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


@SpringBootTest
@Import({TestMailConfig.class}) // เอา TestJWTConfig ออกไปก่อนก็ได้
class ProjectSeApplicationTests {

	// Mock Bean จากปัญหาก่อนหน้า
	@MockBean
	private S3Client s3Client;
	@MockBean
	private S3Presigner s3Presigner;

	// เพิ่ม MockBean ตัวนี้
	@MockBean
	private JWTService jwtService;

	@Test
	void contextLoads() {
		// Context จะโหลดผ่านเพราะ AuthController จะได้ Mock JWTService ไปใช้งาน
	}
}