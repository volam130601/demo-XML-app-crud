package com.example.demoxmlapp.service;

import com.example.demoxmlapp.entity.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodServiceImplTest {

    @Autowired
    FoodServiceImpl foodService;

    @Test
    void getFoods() {
        foodService.getFoods().forEach(System.out::println);
    }

    @Test
    void findOneById() {
        System.out.println(foodService.findOneById(2L));
    }

    @Test
    void save() {
        foodService.save(Food.builder()
                .name("123")
                .price(123d)
                .description("123 123")
                .calories(123)
                .build());
        foodService.getFoods().forEach(System.out::println);
    }

    @Test
    void update() {
        foodService.update(Food.builder()
                .id(2L)
                .name("123")
                .price(123d)
                .description("123 123")
                .calories(123)
                .build());
        foodService.getFoods().forEach(System.out::println);
    }

    @Test
    void delete() {
        foodService.delete(3L);
        foodService.getFoods().forEach(System.out::println);
    }
}