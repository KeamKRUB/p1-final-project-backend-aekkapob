package ku.cs.project_SE.service.notification;

import ku.cs.project_SE.dto.notification.NotificationMessage;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.notification.NotificationChannel;
import org.springframework.stereotype.Service;

@Service
public class SmsSender implements NotificationSender {

    @Override
    public boolean canSend(User user) {
        return user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty();
    }

    @Override
    public void send(User user, NotificationMessage message) {
        System.out.println("Sending SMS to " + user.getPhoneNumber() + ": " + message.content());
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}