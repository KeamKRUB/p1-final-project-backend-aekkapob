package ku.cs.project_SE.service.notification;

import ku.cs.project_SE.dto.notification.NotificationMessage;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.notification.NotificationChannel;

public interface NotificationSender {
    boolean canSend(User user);
    void send(User user, NotificationMessage message);
    NotificationChannel getChannel();
}
