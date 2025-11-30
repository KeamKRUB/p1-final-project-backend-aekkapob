// keam
package ku.cs.project_SE.entity.house;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "house_rental")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HouseRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", unique = true, nullable = false)
    @JsonBackReference
    private House house;

    @Column(name = "rental_yield", precision = 12, scale = 2)
    private BigDecimal rentalYield;

    @Column(name = "property_tax", precision = 12, scale = 2)
    private BigDecimal propertyTax;

    @Column(name = "maintenance_cost", precision = 12, scale = 2)
    private BigDecimal maintenanceCost;

    @Column(name = "additional_data", length = 2000)
    private String additionalData;
}
