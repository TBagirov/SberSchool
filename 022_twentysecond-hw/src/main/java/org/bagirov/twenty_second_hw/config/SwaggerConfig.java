package org.bagirov.twenty_second_hw.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        servers = @Server(
                url = "http://localhost:8080"
        ),
        info = @Info(
                title = "Sensor API",
                description = "Сервис взаимодействия с сенсорами",
                version = "1.0.0",
                contact = @Contact(
                        name = "Багиров Теймур",
                        email = "t.bagirov2000@gmail.com",
                        url = "https://github.com/TBagirov"
                )
        )
)
public class SwaggerConfig {}
