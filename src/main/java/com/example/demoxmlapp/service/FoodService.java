package com.example.demoxmlapp.service;

import com.example.demoxmlapp.entity.Food;

import java.util.List;

public interface FoodService {
    List<Food> getFoods();

    void setFoods();

    void save(Food food);

    void update(Food food);

    void delete(Long foodId);

    Food findOneById(Long foodId);

    List<Food> findBySearchValue(String key);
}
