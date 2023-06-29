package com.digicore.billent.backoffice.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.digicore","com.digicore.billent.data.lib","com.digicore.billent.backoffice.service","com.digicore.registhentication"},exclude= {UserDetailsServiceAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class BillentBackofficeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillentBackofficeServiceApplication.class, args);
	}

}
