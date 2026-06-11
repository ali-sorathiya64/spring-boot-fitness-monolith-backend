package com.example.jpademo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customAPI (){
        return new OpenAPI().info(new Info()
                .title("Fitness Tracking API project")
                .version("v.10")
                .description("Production Grade Backend API's")
                .contact(new Contact()
                        .name("Ali Sorathiya")
                        .url("https://aliportfolioapp.vercel.app/")
                        .email("sorathiyaali97@gmail.com")
                )
                .license(new License().name("Apache 2.0")
                        .url("https://aliportfolioapp.vercel.app/"))


        );
    }

}
