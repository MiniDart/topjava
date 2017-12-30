package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javawebinar.topjava.web.json.JsonUtil.writeIgnoreProps;
import static ru.javawebinar.topjava.web.json.JsonUtil.writeValue;

public class TestUtil {

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static <T> ResultMatcher compareJson(T[] expected, String... ignoreProps) {
        return compareJson(Arrays.asList(expected),ignoreProps);
    }

    public static <T> ResultMatcher compareJson(T expected, String... ignoreProps) {
        return content().json(writeIgnoreProps(expected, ignoreProps));
    }
    public static <T> ResultMatcher compareJson(Collection<T> expected, String... ignoreProps){
        return content().json(writeIgnoreProps(expected, ignoreProps));
    }
}
