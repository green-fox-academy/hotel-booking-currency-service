package com.hiddenite;

import com.hiddenite.model.Heartbeat;
import com.hiddenite.repository.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyApplication implements CommandLineRunner {

	private final HeartbeatRepository heartbeatRepository;

	@Autowired
	public CurrencyApplication(HeartbeatRepository heartbeatRepository) {
		this.heartbeatRepository = heartbeatRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	heartbeatRepository.save(new Heartbeat());
	}
}
