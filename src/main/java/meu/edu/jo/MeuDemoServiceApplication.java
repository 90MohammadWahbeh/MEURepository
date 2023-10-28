package meu.edu.jo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan("meu.edu.jo")
public class MeuDemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuDemoServiceApplication.class, args);
	}

}
