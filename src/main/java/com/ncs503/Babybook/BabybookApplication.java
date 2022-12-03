package com.ncs503.Babybook;

import com.ncs503.Babybook.auth.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BabybookApplication {

	public static void main(String[] args) {

		SpringApplication.run(BabybookApplication.class, args);
	}

}
