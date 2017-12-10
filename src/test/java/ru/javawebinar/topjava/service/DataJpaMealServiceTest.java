package ru.javawebinar.topjava.service;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({Profiles.DATAJPA})
public class DataJpaMealServiceTest extends MealServiceTest {
    static {
        className="DataJpaMealService";
    }

    @Override
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
        assertThat(ADMIN_ID).isEqualTo(actual.getUser().getId());

    }
}
