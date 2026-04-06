package edu.thanglong.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "edu.thanglong.infrastructure.repository.mongo")
@EnableMongoAuditing
public class MongoConfig {
}