package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.DATA;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
    @Autowired
    MealService service;

    @Before
    public void clear(){
        MealTestData.init();
    }
    @Test
    public void get() throws Exception {
        Meal expected=DATA.get(ADMIN).get(0);
        Meal actual=service.get(expected.getId(),ADMIN.getId());
        assertMatch(actual,expected);
        expected=DATA.get(USER).get(0);
        actual=service.get(expected.getId(),USER.getId());
        assertMatch(actual,expected);
    }
    @Test
    public void delete() throws Exception {
        Meal toDel=DATA.get(ADMIN).remove(0);
        service.delete(toDel.getId(),ADMIN.getId());
        assertMatch(service.getAll(ADMIN.getId()),DATA.get(ADMIN));
        toDel=DATA.get(USER).remove(0);
        service.delete(toDel.getId(),USER.getId());
        assertMatch(service.getAll(USER.getId()),DATA.get(USER));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime start=LocalDateTime.of(2017,8,1,18,0,0);
        LocalDateTime end=LocalDateTime.of(2017,8,2,13,0,0);
        List<Meal> expected=DATA.get(USER).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(),start,end))
                .collect(Collectors.toList());
        List<Meal> actual=service.getBetweenDateTimes(start,end,USER.getId());
        assertMatch(actual,expected);
    }
    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(ADMIN.getId()),DATA.get(ADMIN));
    }
    @Test
    public void update() throws Exception {
        Meal expected=DATA.get(ADMIN).get(1);
        expected.setDescription("Второй завтрак");
        service.update(expected,ADMIN.getId());
        assertMatch(service.get(expected.getId(),ADMIN.getId()),expected);
        expected=DATA.get(USER).get(1);
        expected.setDescription("Второй завтрак");
        service.update(expected,USER.getId());
        assertMatch(service.get(expected.getId(),USER.getId()),expected);
    }

    @Test
    public void create() throws Exception {
        DATA.get(ADMIN).add(service.create(new Meal(LocalDateTime.of(2017, 5, 6, 12, 0, 0), "Ланч"
                , 2000), ADMIN.getId()));
        assertMatch(service.getAll(ADMIN.getId()),DATA.get(ADMIN));
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundDueToWrongId() throws Exception {
        service.get(1,ADMIN.getId());
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundDueToWrongUserId() throws Exception {
        service.get(DATA.get(ADMIN).get(0).getId(),1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundDueToId(){
        service.delete(1,ADMIN.getId());
    }
    @Test(expected = NotFoundException.class)
    public void deleteNotFoundDueToUserId(){
        service.delete(DATA.get(ADMIN).get(0).getId(),1);
    }

    public void getAllNotFound(){
        assertThat(service.getAll(1).size()).isEqualTo(0);
    }

    public void getBetweenDateTimeNotFound(){
        LocalDateTime start=LocalDateTime.of(2017,8,1,18,0,0);
        LocalDateTime end=LocalDateTime.of(2017,8,2,13,0,0);
        assertThat(service.getBetweenDateTimes(start,end,1).size()).isEqualTo(0);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound(){
        service.update(DATA.get(ADMIN).get(0),USER.getId());
    }

}