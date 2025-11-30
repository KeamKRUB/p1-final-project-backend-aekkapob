// keam
package ku.cs.project_SE.dto;

import java.util.List;

public record PageResponseDto<T>(
        List<T> items,
        int page,
        int pageSize,
        long totalItems,
        int totalPages
) {
    public static <T> PageResponseDto<T> of(List<T> items, int page, int pageSize, long totalItems) {
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
        return new PageResponseDto<>(items, page, pageSize, totalItems, totalPages);
    }
}
