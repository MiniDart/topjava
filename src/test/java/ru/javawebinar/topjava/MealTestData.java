package ru.javawebinar.topjava;

import org.apache.commons.lang3.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

public class MealTestData {
    public static Map<User,List<Meal>> DATA;
    static {
        init();
    }
    public static void init(){
        DATA=new HashMap<>();
        DATA.put(ADMIN, new ArrayList<>(Arrays.asList(
                new Meal(100010,getLocalDateTime("2017-08-02 18:50:00"),"Ужин",510),
                new Meal(100009,getLocalDateTime("2017-08-02 12:10:00"),"Обед",1000),
                new Meal(100008,getLocalDateTime("2017-08-02 08:30:00"),"Завтрак",500),
                new Meal(100013,getLocalDateTime("2017-08-01 18:10:00"),"Ужин",500),
                new Meal(100012,getLocalDateTime("2017-08-01 12:20:00"),"Обед",1000),
                new Meal(100011,getLocalDateTime("2017-08-01 08:00:00"),"Завтрак",500)
        )));
        DATA.put(USER,new ArrayList<>(Arrays.asList(
                new Meal(100007,getLocalDateTime("2017-08-02 18:20:00"),"Ужин",510),
                new Meal(100006,getLocalDateTime("2017-08-02 12:30:00"),"Обед",1000),
                new Meal(100005,getLocalDateTime("2017-08-02 08:10:00"),"Завтрак",500),
                new Meal(100004,getLocalDateTime("2017-08-01 18:20:00"),"Ужин",500),
                new Meal(100003,getLocalDateTime("2017-08-01 12:30:00"),"Обед",1000),
                new Meal(100002,getLocalDateTime("2017-08-01 08:10:00"),"Завтрак",500)
        )));
    }
    private static LocalDateTime getLocalDateTime(String date){
        return LocalDateTime.parse(StringUtils.join(date.split(" "),"T"));
    }

    public static void main(String[] args) {
        for (int i=0;i<6;i++){
            System.out.println(DATA.get(ADMIN).get(i).equals(DATA.get(ADMIN).get(i)));
        }
    }
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
