package com.hiddenite;

//import com.hiddenite.repository.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyApplication implements CommandLineRunner {

/*	@Autowired
	HeartbeatRepository heartbeatRepository;*/

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		hearthbeatRepository.save(new Heartbeat());
	}
}
