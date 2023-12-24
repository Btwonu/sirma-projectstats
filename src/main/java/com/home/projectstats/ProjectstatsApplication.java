package com.home.projectstats;

import com.home.projectstats.util.DataLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectstatsApplication {
	private static DataLoader dataLoader;

	ProjectstatsApplication(DataLoader dataLoader) {
		ProjectstatsApplication.dataLoader = dataLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectstatsApplication.class, args);
		dataLoader.init();
	}
}
