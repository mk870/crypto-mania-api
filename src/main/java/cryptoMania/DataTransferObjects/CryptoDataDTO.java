package cryptoMania.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDataDTO {
  private String name;
  private String mktCap;
  private String rank;
  private String allTimeHighPrice;
  private String coinsInCirculation;
 }
