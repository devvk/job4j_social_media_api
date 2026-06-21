package ru.job4j.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html");
	}

    @Scheduled(fixedRate = 10_000)
    public void reportCurrentTime() {
        System.out.println("Текущее время: " + new java.util.Date());
        System.out.println("Thread name: " + Thread.currentThread().getName());
    }
}
