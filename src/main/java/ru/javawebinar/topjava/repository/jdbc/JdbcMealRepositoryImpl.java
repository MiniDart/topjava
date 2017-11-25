package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

@Repository("jdbcMealRepository")
public class JdbcMealRepositoryImpl implements MealRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;
    private final RowMapper<Meal> ROW_MAPPER= (resultSet, i) -> new Meal(resultSet.getInt("id"),
            LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("date_time").getTime()),
            TimeZone.getDefault().toZoneId()),resultSet.getString("description"),resultSet.getInt("calories"));
    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.insertMeal=new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate=jdbcTemplate;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("description", meal.getDescription())
                .addValue("dateTime", meal.getDateTime())
                .addValue("calories",meal.getCalories());
        if (meal.isNew()){
            Number k=insertMeal.executeAndReturnKey(map);
            meal.setId(k.intValue());
        }
        else {
            map.addValue("id",meal.getId());
            meal=namedParameterJdbcTemplate.update("UPDATE meals SET description=:description, date_time=:dateTime," +
                    "calories=:calories WHERE id=:id AND user_id=:userId",map)==1?meal:null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id,userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals=jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?",ROW_MAPPER,
                id,userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC",ROW_MAPPER,userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND (date_time>=? AND date_time<=?) " +
                        "ORDER BY date_time DESC",ROW_MAPPER,
                userId,Timestamp.valueOf(startDate.format(formatter)),Timestamp.valueOf(endDate.format(formatter)));
    }
}
