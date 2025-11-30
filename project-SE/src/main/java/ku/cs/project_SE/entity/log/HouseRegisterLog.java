//package ku.cs.project_SE.entity.log;
//
//import jakarta.persistence.*;
//import ku.cs.project_SE.entity.user.User;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "house_register_log")
//public class HouseRegisterLog {
//
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    private UUID logId;
//
//    @Column(name = "action", nullable = false)
//    private String action;
//
//    @Column(name = "register_id", nullable = false)
//    private UUID registerId;
//
//    @ManyToOne
//    @JoinColumn(name = "performed_by")
//    private User performedBy;
//
//    @Column(name = "timestamp")
//    @CreationTimestamp
//    private LocalDateTime timestamp;
//
//    @Column(name = "snapshot", columnDefinition = "TEXT")
//    private String snapshot;
//}