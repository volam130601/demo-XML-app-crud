package com.example.demoxmlapp;

import com.example.demoxmlapp.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoXmlAppApplication implements CommandLineRunner {
    @Autowired
    FoodService foodService;

    public static void main(String[] args) {
        SpringApplication.run(DemoXmlAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        foodService.setFoods();
    }
}
