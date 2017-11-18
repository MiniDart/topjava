package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    Meal create(Meal meal);
    void delete(int id, int userId) throws NotFoundException;
    Meal get(int id, int userId) throws NotFoundException;
    List<Meal> getAll(int userId);
    void update(Meal meal, int userId);
    List<Meal> getByDate(LocalDateTime start, LocalDateTime end, int userId);
}