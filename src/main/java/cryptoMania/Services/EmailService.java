package cryptoMania.Services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import cryptoMania.Emails.SignupEmail;
import cryptoMania.Entities.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private SignupEmail signupEmail;
  public String sendSignUpEmail (User user,String token) throws MessagingException{
    String url = "https://react-cryptomania.netlify.app/verification/"+ token;
    try{
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
      helper.setText(signupEmail.signUpemailhtml(user.getFirstName(), url), true);
      helper.setTo(user.getEmail());
      helper.setSubject("Confirm your email for Account Registration");
      helper.setFrom("webdevndlovu5@gmail.com");
      mailSender.send(mimeMessage);
      return "ok";
    }catch(MailSendException e){
      log.info("failed to send email",e.getMessage());
      return e.getMessage();
    }
  }
}
