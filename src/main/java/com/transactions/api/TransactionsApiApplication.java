package com.transactions.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableWebFlux
@EnableAutoConfiguration
public class TransactionsApiApplication {

	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	{
		PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("application.properties"));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
	public static void main(String[] args) {
		SpringApplication.run(TransactionsApiApplication.class, args);
	}

}
