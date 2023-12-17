package com.home.projectstats;

import com.home.projectstats.util.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class ProjectstatsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectstatsApplication.class, args);

		try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/input.csv"), true)) {
			String[] line;
			while ((line = csvReader.readLine()) != null) {
				System.out.println(Arrays.toString(line));
			}
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}
}
