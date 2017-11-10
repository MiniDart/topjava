package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.crud.meals.MealsCrud;
import ru.javawebinar.topjava.crud.meals.MemoryMealsCrud;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditServlet extends HttpServlet{
    private static MealsCrud mealsCrud=new MemoryMealsCrud();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meal",mealsCrud.get(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("/edit.jsp").forward(req,resp);
    }
}
