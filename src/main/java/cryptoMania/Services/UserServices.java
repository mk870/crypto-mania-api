package cryptoMania.Services;



import javax.mail.MessagingException;

import cryptoMania.DataTransferObjects.LoginDTO;
import cryptoMania.DataTransferObjects.SignUpDTO;
import cryptoMania.DataTransferObjects.UserDTO;

import cryptoMania.Entities.VerificationToken;


public interface UserServices {
  public String saveUser(UserDTO user) throws MessagingException;
  public VerificationToken saveVerificationToken(VerificationToken verificationToken);
  public SignUpDTO validateToken(String token);
  public SignUpDTO userLogin(LoginDTO loginDTO);
}
