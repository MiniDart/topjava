package ru.javawebinar.topjava.crud.meals;

import ru.javawebinar.topjava.data.MemoryData;
import ru.javawebinar.topjava.model.Meal;

public class MemoryMealsCrud implements MealsCrud{
    @Override
    public void create(Meal meal) {
        MemoryData.add(meal);
    }

    @Override
    public void update(Meal meal) {
        MemoryData.update(meal);
    }

    @Override
    public void delete(int id) {
        MemoryData.delete(id);
    }

    @Override
    public Meal get(int id) {
        return MemoryData.getMeal(id);
    }
}
