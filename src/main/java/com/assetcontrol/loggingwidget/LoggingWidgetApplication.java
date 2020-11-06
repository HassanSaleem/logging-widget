package com.assetcontrol.loggingwidget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
public class LoggingWidgetApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoggingWidgetApplication.class, args);
		LoggingThread m1= new LoggingThread();
		Thread t1 =new Thread(m1);
		t1.start();
	}

}
