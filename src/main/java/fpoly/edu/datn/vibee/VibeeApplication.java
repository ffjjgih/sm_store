package fpoly.edu.datn.vibee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VibeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VibeeApplication.class, args);
	}

}
