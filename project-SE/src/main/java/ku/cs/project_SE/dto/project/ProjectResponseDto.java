// keam&tee
package ku.cs.project_SE.dto.project;

public record ProjectResponseDto (
    String projectName,
    String projectDescription,
    Long imageId,
    String imageUrl
) {}
