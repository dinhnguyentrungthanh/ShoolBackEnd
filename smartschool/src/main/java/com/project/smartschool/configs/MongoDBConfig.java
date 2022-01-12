package com.project.smartschool.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.project.smartschool.repository"})
public class MongoDBConfig {

}
