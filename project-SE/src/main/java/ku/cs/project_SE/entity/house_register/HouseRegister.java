package ku.cs.project_SE.entity.house_register;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.log.LoggableEntity;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.HouseRegisterStatus;
import ku.cs.project_SE.enums.VerifyUserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HouseRegister implements LoggableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "register_id", updatable = false, nullable = false)
    private UUID registerId;

    @Column(name = "register_status")
    @Enumerated(EnumType.STRING)
    private HouseRegisterStatus registerStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @Override
    public UUID getId() {
        return registerId;
    }
}
