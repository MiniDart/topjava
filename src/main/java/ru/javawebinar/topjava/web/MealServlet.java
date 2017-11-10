package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.crud.meals.Command;
import ru.javawebinar.topjava.crud.meals.MealsCrud;
import ru.javawebinar.topjava.crud.meals.MemoryMealsCrud;
import ru.javawebinar.topjava.data.MemoryData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MealServlet extends HttpServlet{
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static MealsCrud mealsCrud=new MemoryMealsCrud();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(new ArrayList<>(MemoryData.getMeals().values()), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        req.setAttribute("mealsList",mealsWithExceeded);
        req.getRequestDispatcher("/meals.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command=Command.getCommand(req.getParameter("action"));
        switch (command){
            case CREATE:
                LocalDateTime dateTimeCreate=getLocalDateTime(req.getParameter("date"));
                mealsCrud.create(new Meal(dateTimeCreate,req.getParameter("description"),Integer.parseInt(req.getParameter("calories")),-1));
                break;
            case UPDATE:
                LocalDateTime dateTimeUpdate=getLocalDateTime(req.getParameter("date"));
                LOG.debug(req.getParameter("description"));
                mealsCrud.update(new Meal(dateTimeUpdate,req.getParameter("description"),Integer.parseInt(req.getParameter("calories")),Integer.parseInt(req.getParameter("id"))));
                break;
        }
        resp.sendRedirect("meals");
    }
    private LocalDateTime getLocalDateTime(String param){
        String[] fullData=param.split(" ");
        String[] date=fullData[0].split("\\.");
        String[] time=fullData[1].split(":");
        return LocalDateTime.of(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]),Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
    }
}
