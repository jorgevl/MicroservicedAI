package com.tryjorgecatch.microserviceaicore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tryjorgecatch.microserviceaicore.algorithm.EvolutiveAlgorithm;


@SpringBootApplication
public class MicroserviceAiCoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MicroserviceAiCoreApplication.class, args); 
		new EvolutiveAlgorithm().start();
		ctx.close();
	}

}
