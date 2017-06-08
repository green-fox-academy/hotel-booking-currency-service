package com.hiddenite;

import com.hiddenite.model.Hearthbeat;
import com.hiddenite.repository.HearthbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyApplication implements CommandLineRunner {

	@Autowired
	HearthbeatRepository hearthbeatRepository;
	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		hearthbeatRepository.save(new Hearthbeat());
	}
}
