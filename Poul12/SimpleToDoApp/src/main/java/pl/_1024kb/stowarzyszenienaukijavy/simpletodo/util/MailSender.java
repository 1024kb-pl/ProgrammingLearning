package pl._1024kb.stowarzyszenienaukijavy.simpletodo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl._1024kb.stowarzyszenienaukijavy.simpletodo.exception.NotFoundDesiredDataRuntimeException;
import pl._1024kb.stowarzyszenienaukijavy.simpletodo.repository.MailDataRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailSender
{
    private MailDataRepository mailRepository;

    @Autowired
    public MailSender(MailDataRepository mailRepository)
    {
        this.mailRepository = mailRepository;
    }

    public final static String MESSAGE = "Success";
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    public String sendEmail(String receiverAddress, String newPassword) throws MessagingException
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "t.pl");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "t.pl");

        String addressName = mailRepository.findById(1L).orElseThrow(NotFoundDesiredDataRuntimeException::newRunTimeException).getAddressName();
        //String password = mailRepository.findById(1L).orElseThrow(NotFoundDesiredDataRuntimeException::newRunTimeException).getPassword();
        String password = System.getenv("PASS_FOR_MAIL");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(addressName, password);
                    }
                });

        try
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(addressName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress));
            message.setSubject("Reset Password");
            message.setText("You requested for a password reset.\n\nYour new password is: " + newPassword);

            Transport.send(message);

            System.out.println("Mail sent");
            logger.info("Mail sent to {}", receiverAddress);
            return MESSAGE;

        } catch(MessagingException e)
        {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new MessagingException();
        }
    }
}
