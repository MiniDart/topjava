package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class ResourceControllerTest extends AbstractControllerTest{
    @Test
    public void getStyleTest() throws Exception{
        mockMvc.perform(get("/resources/css/style.css"))
                .andExpect(status().isOk());
    }
}
