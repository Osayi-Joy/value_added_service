package com.digicore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
//scanBasePackages = {"com.digicore","com.digicore.billent.data.lib","com.digicore.billent.backoffice.service","com.digicore.registhentication"},
@EnableAsync
@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class BillentBackofficeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillentBackofficeServiceApplication.class, args);
	}

}
