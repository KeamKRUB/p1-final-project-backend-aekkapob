package ku.cs.project_SE.service.notification;

import ku.cs.project_SE.dto.notification.NotificationMessage;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.notification.NotificationChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmailSender implements NotificationSender {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean canSend(User user) {
        return user.getEmail() != null && !user.getEmail().isEmpty();
    }

    @Override
    public void send(User user, NotificationMessage message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject(message.subject());
        msg.setText(message.content());
        mailSender.send(msg);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }
}
