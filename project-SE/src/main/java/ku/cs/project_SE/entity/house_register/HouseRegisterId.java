package ku.cs.project_SE.entity.house_register;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//Tee
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRegisterId implements java.io.Serializable {
    @Column(name = "user_id", columnDefinition = "UUID")
    private UUID userId;

    @Column(name = "house_id")
    private long houseId;
}
