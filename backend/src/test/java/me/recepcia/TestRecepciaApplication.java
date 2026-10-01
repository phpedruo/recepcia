package me.recepcia;

import org.springframework.boot.SpringApplication;

public class TestRecepciaApplication {

	public static void main(String[] args) {
		SpringApplication.from(RecepciaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
