// keam
package ku.cs.project_SE.dto.house;

import java.math.BigDecimal;
import java.util.Map;

public record RentalDto(
        BigDecimal rentalYield,
        BigDecimal propertyTax,
        BigDecimal maintenanceCost,
        String additionalData
) {}
