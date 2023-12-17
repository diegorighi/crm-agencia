package br.com.agencia.crm.agenciacrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AgenciaCrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaCrmApplication.class, args);
	}

}
