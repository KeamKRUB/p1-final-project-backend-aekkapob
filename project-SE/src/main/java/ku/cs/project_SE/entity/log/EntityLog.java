package ku.cs.project_SE.entity.log;

import jakarta.persistence.*;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.ActionType;
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
@Table(name = "entity_log")
public class EntityLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID logId;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false)
    private UUID entityId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType action;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column(columnDefinition = "TEXT")
    private String snapshot; // JSON

    @CreationTimestamp
    private LocalDateTime timestamp;
}
