package net.lerkasan.capstone.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService implements EmailServiceI {

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";
    public static final String MAIL_SENT_SUCCESSFULLY = "Mail sent successfully.";
    public static final String ERROR_WHILE_SENDING_MAIL = "Error while sending mail.";
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendSimpleMail(EmailDetails details) {
        MimeMessage mailMessage = mailSender.createMimeMessage();

        try {
            mailMessage.setFrom(new InternetAddress(sender));
            mailMessage.setRecipients(MimeMessage.RecipientType.TO, details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setContent(details.getMessageBody(), TEXT_HTML_CHARSET_UTF_8);

            mailSender.send(mailMessage);
            log.info(MAIL_SENT_SUCCESSFULLY);
            return MAIL_SENT_SUCCESSFULLY;
        } catch (MessagingException | MailException e) {
            return ERROR_WHILE_SENDING_MAIL;
        }
    }
}
