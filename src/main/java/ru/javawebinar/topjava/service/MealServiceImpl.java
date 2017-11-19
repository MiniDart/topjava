package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(Meal meal, int userId) {
        meal.setUserId(userId);
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        get(id,userId);
        checkNotFoundWithId(repository.delete(id),id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id,userId),id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException{
        get(meal.getId(),userId);
        meal.setUserId(userId);
        checkNotFoundWithId(repository.save(meal),meal.getId());
    }

    @Override
    public List<Meal> getByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        if (startDate ==null) startDate =LocalDate.MIN;
        if (endDate ==null) endDate =LocalDate.MAX;
        if (startTime==null) startTime=LocalTime.MIN;
        if (endTime==null) endTime=LocalTime.MAX;
        return repository.getByDate(startDate, endDate,startTime,endTime, userId);
    }
}