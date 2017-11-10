package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryData {
    private static AtomicInteger generatedId;

    public static Map<Integer, Meal> getMeals() {
        return meals;
    }

    private static Map<Integer,Meal> meals;
    static {
        generatedId=new AtomicInteger(0);
        meals=new ConcurrentHashMap<>();
        Meal[] arrMeals={new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,generatedId.getAndIncrement()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,generatedId.getAndIncrement()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,generatedId.getAndIncrement()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,generatedId.getAndIncrement()),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,generatedId.getAndIncrement())};
        for (int i=0;i<arrMeals.length;i++){
            meals.put(arrMeals[i].getId(),arrMeals[i]);
        }
    }
    public static void add(Meal meal){
        int id=generatedId.getAndIncrement();
        meal.setId(id);
        meals.put(id,meal);
    }
    public static Meal getMeal(int id){
        return meals.get(id);
    }
    public static void update(Meal meal){
        meals.put(meal.getId(),meal);
    }
    public static void delete(int id){
        meals.remove(id);
    }
}
