package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id);

    Meal get(int id, int userId);

    List<Meal> getByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId);
    List<Meal> getAll(int userId);
}
