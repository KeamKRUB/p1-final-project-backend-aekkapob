package ku.cs.project_SE.service.notification;

import ku.cs.project_SE.dto.notification.NotificationMessage;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.notification.NotificationChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final Map<NotificationChannel, NotificationSender> sendersMap;

    @Autowired
    public NotificationService(List<NotificationSender> senders) {
        this.sendersMap = senders.stream()
                .collect(Collectors.toMap(NotificationSender::getChannel, Function.identity()));
    }

    private void sendNotification(User user, NotificationMessage message, NotificationChannel channel) {
        NotificationSender sender = sendersMap.get(channel);

        if (sender == null) {
            System.err.println("No sender configured for channel: " + channel);
            return;
        }

        if (sender.canSend(user)) {
            sender.send(user, message);
        } else {
            System.err.println("Cannot send to user via channel: " + channel);
        }
    }

    public void approveNotifyUser(User user, NotificationChannel channel) {
        String subject = "Your investor request on Aekkapob has been approved";
        String body = String.format(
                "Hello %s,\n\nYour investor application on Aekkapob has been approved.\nYou can now log in and start using investor features.\n\nRegards,\nAekkapob Team",
                user.getFirstName()
        );
        NotificationMessage message = new NotificationMessage(subject, body);

        sendNotification(user, message, channel);
    }

    public void rejectNotifyUser(User user, NotificationChannel channel) {
        String subject = "Your investor request on Aekkapob has been rejected";
        String body = String.format(
                "Hello %s,\n\nYour investor application on Aekkapob has been rejected..\n\nRegards,\nAekkapob Team",
                user.getFirstName()
        );
        NotificationMessage message = new NotificationMessage(subject, body);

        sendNotification(user, message, channel);
    }

    public void approveNotifyUserAllChannels(User user) {
        String subject = "Your investor request on Aekkapob has been approved";
        String body = String.format(
                "Hello %s,\n\nYour investor application on Aekkapob has been rejected..\n\nRegards,\nAekkapob Team",
                user.getFirstName()
        );
        NotificationMessage message = new NotificationMessage(subject, body);

        for (NotificationSender sender : sendersMap.values()) {
            if (sender.canSend(user)) {
                sender.send(user, message);
            }
        }
    }

    public void rejectNotifyUserAllChannels(User user, String reason) {
        String subject = "Your investor request on Aekkapob has been rejected";
        String body = String.format(
                "Hello %s,\n\nYour investor application on Aekkapob has been rejected because %s\n\nRegards,\nAekkapob Team",
                user.getFirstName(),reason
        );
        NotificationMessage message = new NotificationMessage(subject, body);

        for (NotificationSender sender : sendersMap.values()) {
            if (sender.canSend(user)) {
                sender.send(user, message);
            }
        }
    }



}