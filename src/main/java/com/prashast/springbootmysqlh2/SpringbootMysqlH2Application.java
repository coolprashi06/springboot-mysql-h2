package com.prashast.springbootmysqlh2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class SpringbootMysqlH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMysqlH2Application.class, args);
	}

}
