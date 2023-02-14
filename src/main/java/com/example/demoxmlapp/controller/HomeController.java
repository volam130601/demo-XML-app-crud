package com.example.demoxmlapp.controller;

import com.example.demoxmlapp.entity.Food;
import com.example.demoxmlapp.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private FoodService foodService;

    void viewHomepage(ModelMap model) {
        model.addAttribute("foods", foodService.getFoods());
        if (model.getAttribute("message") == null) model.addAttribute("message", null);
    }

    @GetMapping("/")
    public String homePage(ModelMap model) {
        model.addAttribute("key", null);
        viewHomepage(model);
        return "index";
    }

    @GetMapping("create")
    public String createFood(Model model, Food food) {
        model.addAttribute("food", food);
        return "edit";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam("id") Long foodID, Model model) {
        model.addAttribute("food", foodService.findOneById(foodID));
        return "edit";
    }

    @PostMapping("save")
    public String saveOrUpdate(ModelMap model, @ModelAttribute Food food) {
        if (food.getId() != null) {
            foodService.update(food);
            model.addAttribute("message", "Update food is success!");
        } else {
            foodService.save(food);
            model.addAttribute("message", "Create new food is success!");
        }
        viewHomepage(model);
        return "index";
    }

    @GetMapping("delete")
    public String delete(ModelMap model, @RequestParam("id") Long foodID) {
        foodService.delete(foodID);
        model.addAttribute("message", "Delete food by id: " + foodID + " is success!");
        viewHomepage(model);
        return "index";
    }


    @GetMapping("search")
    public String searchName(ModelMap model, @RequestParam("search") String key) {
        model.addAttribute("foods", foodService.findBySearchValue(key));
        model.addAttribute("key", key);
        return "index";
    }
}
