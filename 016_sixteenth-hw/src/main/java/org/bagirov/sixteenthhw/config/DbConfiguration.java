package org.bagirov.sixteenthhw.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DbConfiguration extends DriverManagerDataSource {


    public DbConfiguration() {

    }
}
