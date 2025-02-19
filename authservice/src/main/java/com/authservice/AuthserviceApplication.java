package com.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthserviceApplication {

	/*
	 * ===========================================================================
	 * ======================== Module : Auth-Servic =============================
	 * ======================== Created By : Umesh Kumar =========================
	 * ======================== Created On : 04-06-2024 ==========================
	 * ===========================================================================
	 * | Code Status : On
	 */
 	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

}
