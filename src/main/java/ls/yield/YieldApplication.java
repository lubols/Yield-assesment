package ls.yield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * The main class, entry point of the application
 */
@SpringBootApplication
@EnableScheduling
public class YieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(YieldApplication.class, args);
	}

}
