package ku.cs.project_SE.entity.house;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.log.LoggableEntity;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.house.HouseStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

//Tee
@Entity
@Table(name = "house_request")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseRequest implements LoggableEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User requester;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HouseStatus status;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "admin_comment", length = 1000)
    private String adminComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver")
    private User approver;

    @Override
    public UUID getId() {
        return requestId;
    }
}

