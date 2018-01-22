package no.ntnu.unnamedsoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:app-config.xml")
public class RussesamfunnetApiApp {

	public static void main(String[] args) {
		
		SpringApplication.run(RussesamfunnetApiApp.class, args);

	}

}
