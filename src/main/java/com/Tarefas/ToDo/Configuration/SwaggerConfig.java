package com.Tarefas.ToDo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
   @Bean
   public OpenAPI tasksOpenApi() {
      return new OpenAPI().info(new Info()
         .title("To-Do List")
         .description("API de lista de tarefas\n\n" +
         "Contato:\n\n" +
         "Lucas Gradilone Valias - lucas.valias@fatec.sp.gov.br\n\n")
        
         .version("v0.0.1")
         .license(new License()
           .name("Apache 2.0").url("http://springdoc.org")));    
}}