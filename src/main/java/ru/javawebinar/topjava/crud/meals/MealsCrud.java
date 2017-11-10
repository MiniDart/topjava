package ru.javawebinar.topjava.crud.meals;

import ru.javawebinar.topjava.model.Meal;

public interface MealsCrud {
    void create(Meal meal);
    void update(Meal meal);
    void delete(int id);
    Meal get(int id);
}
