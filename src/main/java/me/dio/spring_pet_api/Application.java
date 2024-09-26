package me.dio.spring_pet_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Pet Shop API", version = "1.0", description = "API para gerenciamento de um Pet Shop"))
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}
