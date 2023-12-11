package br.com.agencia.crm.agenciacrm.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@PropertySource("classpath:application.properties")
public class DevMongoDBConfiguration {
    
    @Value("${mongodb.defined.uri}")
	private String mongoDbConnectionUri;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoDbConnectionUri));
    }
}
