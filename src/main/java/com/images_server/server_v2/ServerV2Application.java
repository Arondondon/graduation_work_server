package com.images_server.server_v2;

import com.images_server.server_v2.properties.WarningTextProperty;
import com.images_server.server_v2.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, WarningTextProperty.class})
public class ServerV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ServerV2Application.class, args);
	}

}
