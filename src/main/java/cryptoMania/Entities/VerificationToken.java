package cryptoMania.Entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ForeignKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class VerificationToken {
  private static final int EXPIRATION_TIME = 15;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  private Date expirationTime;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id",
             nullable = false,
             foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
  private User user;

  public VerificationToken(String token,User user){
    this.token = token;
    this.user = user;
    this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
  }
  public VerificationToken(String token){
   this.token = token;
   
   this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
 }
  
  private Date calculateExpirationTime(int time){
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(new Date().getTime());
    calendar.add(Calendar.MINUTE,time);
    return new Date(calendar.getTime().getTime());
  }
}
