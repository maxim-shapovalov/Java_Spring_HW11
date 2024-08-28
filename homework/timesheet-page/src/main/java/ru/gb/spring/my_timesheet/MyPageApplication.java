package ru.gb.spring.my_timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MyPageApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyPageApplication.class, args);
	}
}
