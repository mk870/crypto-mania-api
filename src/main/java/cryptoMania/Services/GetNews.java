package cryptoMania.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class GetNews {
  @Autowired
  private WebClient.Builder webClientBuilder;
  private String category = "cryptocurrencies";
  private int year = LocalDate.now().getYear();
  private int month = LocalDate.now().getMonthValue();
  private int day = LocalDate.now().getDayOfMonth();
  private String url = String.format("https://newsapi.org/v2/everything?q=%s&from=%s-%s-%s&sortBy=publishedAt&apiKey=077a482d122145f1b9752f786bc91c87", category,year,month,day);
  public Object newsApi(){
    return webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
  }

}
