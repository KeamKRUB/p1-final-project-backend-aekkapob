// keam
package ku.cs.project_SE.entity.house;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "house_renovate")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HouseRenovate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", unique = true, nullable = false)
    @JsonBackReference
    private House house;

    @Column(name = "innovate_cost", precision = 12, scale = 2)
    private BigDecimal innovateCost;

    @Column(name = "law_information", length = 2000)
    private String lawInformation;

    @Column(name = "structure", length = 1000)
    private String structure;

    @Column(name = "additional_data", length = 2000)
    private String additionalData;
}
