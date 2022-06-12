package cryptoMania;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CryptoManiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoManiaApplication.class, args);
	}
		@Bean
		public WebClient.Builder getWebClient()
		{
		return WebClient.builder();
		}
}
