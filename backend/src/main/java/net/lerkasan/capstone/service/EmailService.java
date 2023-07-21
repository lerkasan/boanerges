package net.lerkasan.capstone.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import net.lerkasan.capstone.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceI {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendSimpleMail(EmailDetails details) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();

//            MimeMessage message = new MimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message);
//            MimeMessage message = sender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message);
        MimeMessage mailMessage = mailSender.createMimeMessage();

        try {
            mailMessage.setFrom(new InternetAddress(sender));
            mailMessage.setRecipients(MimeMessage.RecipientType.TO, details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setContent(details.getMessageBody(), "text/html; charset=utf-8");
//            mailMessage.setText(details.getMessageBody());

            mailSender.send(mailMessage);
            System.out.println("Mail sent successfully...");
            return "Mail Sent Successfully...";
        } catch (MessagingException | MailException e) {
            return "Error while sending mail!!!";
        }
    }

//    public String sendMailWithAttachment(EmailDetails details) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//        try {
//            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setText(details.getMessageBody());
//            mimeMessageHelper.setSubject(
//                    details.getSubject());
//
//            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
//
//            javaMailSender.send(mimeMessage);
//            return "Mail sent Successfully";
//        } catch (MailException | MessagingException e) {
//            return "Error while sending mail!!!";
//        }
//    }
}
