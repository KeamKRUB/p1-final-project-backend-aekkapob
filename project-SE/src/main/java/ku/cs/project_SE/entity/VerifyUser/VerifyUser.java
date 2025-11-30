package ku.cs.project_SE.entity.VerifyUser;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.log.LoggableEntity;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.VerifyUserStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verify_user_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyUser implements LoggableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "request_id", updatable = false, nullable = false)
    private UUID requestId;

    @Column(name = "citizen_id", nullable = false)
    private String citizenId;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private VerifyUserStatus requestStatus;

    @Column(name = "id_card_image")
    private String idCardImage;

    @Column(name = "register_date")
    private LocalDateTime requestDate;

    @ManyToOne
    @JoinColumn(name = "approver", referencedColumnName = "user_id")
    private User approver
            ;

    @ManyToOne
    @JoinColumn(name = "requester", referencedColumnName = "user_id")
    private User requester;

    @Override
    public UUID getId() {
        return requestId;
    }
}
