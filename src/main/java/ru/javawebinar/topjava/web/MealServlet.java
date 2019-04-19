package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealMapDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao mealMapDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealMapDao = new MealMapDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Meal meal;

        if (action == null) {
            LOG.info("get all meals");
            request.setAttribute("meals", MealsUtil.getWithExcess(mealMapDao.getAll(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            switch (action) {
                case "add":
                    LOG.info("add meal");
                    meal = new Meal(LocalDateTime.now(), "", 0);
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                    break;
                case "update":
                    LOG.info("update meal");
                    meal = mealMapDao.getById(parseId(request));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                    break;
                case "delete":
                    LOG.info("delete meal");
                    mealMapDao.delete(parseId(request));
                    response.sendRedirect("meals");
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        int id = parseId(request);

        if (id == 0) {
            LOG.info("save meal");
            mealMapDao.save(meal);
        } else {
            LOG.info("update meal");
            meal.setId(id);
            mealMapDao.update(meal);
        }

        response.sendRedirect("meals");
    }

    private int parseId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}