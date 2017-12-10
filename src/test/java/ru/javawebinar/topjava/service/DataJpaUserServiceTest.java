package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles({Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTest{
    static {
        className="DataJpaUserServiceTest";
    }

    @Override
    public void get() throws Exception {
        User user = service.get(USER_ID);
        assertMatch(user, USER);
        List<Meal> expected=new ArrayList<>(Arrays.asList(MEAL1,MEAL2,MEAL3,MEAL4,MEAL5,MEAL6));
        for (int i=0;i<expected.size();i++){
            assertThat(expected.get(i)).isEqualTo(user.getMeals().get(i));
        }
    }
}
