package cryptoMania.Services;
import java.util.Calendar;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cryptoMania.DataTransferObjects.LoginDTO;
import cryptoMania.DataTransferObjects.SignUpDTO;
import cryptoMania.DataTransferObjects.UserDTO;
import cryptoMania.Entities.User;
import cryptoMania.Entities.VerificationToken;
import cryptoMania.JwtFilter.JwtUtil;
import cryptoMania.Repositories.UserRepository;
import cryptoMania.Repositories.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserServices{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  @Autowired
  EmailService emailService;
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public String saveUser(UserDTO userInfo) throws MessagingException {
    
    User user = new User();
    user.setFirstName(userInfo.getFirstName());
    user.setLastName(userInfo.getLastName());
    user.setEmail(userInfo.getEmail());
    user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    user.setRole("USER");
    User checkingEmail = userRepository.findByEmail(userInfo.getEmail());
    if(checkingEmail != null){
      return "This email already exists";
    }
    User savedUser = userRepository.save(user);
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken(token, user);
    VerificationToken savedVerificationToken = saveVerificationToken(verificationToken);
    String emailStatus = emailService.sendSignUpEmail(savedUser,token);
    
    

    if(emailStatus != "ok"){
      verificationTokenRepository.deleteById(savedVerificationToken.getId());
      userRepository.deleteById(savedUser.getId());
      return "Failed to send email please check your network and try again" ;
      
    }
    
    return "Please check your Email for verification" ;
  }
  @Override
  public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
    return verificationTokenRepository.save(verificationToken);
  }
  
  @Override
  public SignUpDTO validateToken(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    if(verificationToken == null){
      return new SignUpDTO(null, "invalid");
    }
    //now checking for time expiration
    User user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();
    if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime())<= 0){
      verificationTokenRepository.deleteById(verificationToken.getId());
      userRepository.deleteById(user.getId());
      return new SignUpDTO(null, "Your verification period has expired please signup again");
    }
    //else if all is well
    user.setEnabled(true);
    User savedUser = userRepository.save(user);
    
    final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(savedUser.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    System.out.println("jwt: "+jwt);
    return new SignUpDTO(jwt, "verification successful");
  }
  @Override
  public SignUpDTO userLogin(LoginDTO loginDTO) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
      
    } catch (BadCredentialsException e) {
      
      return new SignUpDTO(null,"Incorrect Credentials");
    }
    final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDTO.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    System.out.println(jwt);
    return new SignUpDTO(jwt,"login successful");
    
  }
}
