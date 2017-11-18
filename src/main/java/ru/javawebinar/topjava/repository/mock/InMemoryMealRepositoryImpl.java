package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) meal.setId(counter.getAndIncrement());
        repository.put(meal.getId(),meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(id).getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal=repository.get(id);
        if (meal==null||meal.getUserId()!= userId) return null;
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values()
                .stream()
                .filter((meal -> meal.getUserId()==userId))
                .sorted(((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime())))
                .collect(Collectors.toList());
    }
    @Override
    public List<Meal> getByDate(LocalDateTime start, LocalDateTime end, int userId) {
        log.info("getByDate");
        return repository.values().stream()
                .filter(meal -> meal.getUserId()==userId&&meal.getDateTime().isAfter(start)&&meal.getDateTime().isBefore(end))
                .collect(Collectors.toList());
    }
}

