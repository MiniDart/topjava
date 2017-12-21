package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends MealRestController{

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String meals(Model model, HttpServletRequest request){
        List<MealWithExceed> meals;
        if (request.getParameterNames().hasMoreElements()){
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            meals=getBetween(startDate, startTime, endDate, endTime);
        }
         else {
            meals=getAll();
        }
        model.addAttribute("meals",meals);
        return "meals";
    }

    @GetMapping({"/update/{mealId}","/create"})
    public String getMealForm(Model model, @PathVariable(required = false) Integer mealId){
        final Meal meal = mealId==null ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(mealId);
        model.addAttribute("meal",meal);
        return "mealForm";
    }
    @GetMapping("/delete/{mealId}")
    public RedirectView delete(HttpServletRequest request, @PathVariable Integer mealId){
        delete(mealId);
        return new RedirectView(request.getContextPath()+"/meals");
    }
    @PostMapping
    public RedirectView update(HttpServletRequest request){
        System.out.println("========================++++++++++++in POST");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            create(meal);
        } else {
            update(meal, getId(request));
        }
        return new RedirectView(request.getContextPath()+"/meals");
    }
    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
