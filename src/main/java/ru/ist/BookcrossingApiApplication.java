package ru.ist;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Bookcrossing API",
                version = "1.0.0"
        )
)
public class BookcrossingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookcrossingApiApplication.class, args);
    }
}