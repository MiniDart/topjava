package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.crud.meals.MealsCrud;
import ru.javawebinar.topjava.crud.meals.MemoryMealsCrud;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private static MealsCrud mealsCrud=new MemoryMealsCrud();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mealsCrud.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("meals");
    }
}
