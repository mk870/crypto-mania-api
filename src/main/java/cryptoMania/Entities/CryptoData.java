package cryptoMania.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "watchlist")
public class CryptoData {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String mktCap;
  private String rank;
  private String allTimeHighPrice;
  @Column(name = "coins_in_circulation")
  private String coinsInCirculation;
  @ManyToOne(fetch = FetchType.EAGER,optional = false)
  private User user;

}
