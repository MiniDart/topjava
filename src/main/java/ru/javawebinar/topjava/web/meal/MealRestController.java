package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNewMeal;

public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;
    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }
    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNewMeal(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.create(meal);
    }
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }
    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        service.update(meal, AuthorizedUser.id());
    }

    public List<Meal> getByDate(LocalDateTime start,LocalDateTime end){
        log.info("getByDate");
        return service.getByDate(start,end, AuthorizedUser.id());
    }
    public List<Meal> getAll(){
        log.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }

}