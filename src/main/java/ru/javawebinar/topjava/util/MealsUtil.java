package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final List<Meal> MEAL_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static void main(String[] args) {
        getFilteredWithExceeded(MEAL_LIST, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<Meal> getMealList() {
        return MEAL_LIST;
    }

    public static List<MealTo> getWithExcess(List<Meal> mealList, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    private static List<MealTo> getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime,
                                                        LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = mealList.stream().collect(Collectors.groupingBy(
                Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumPerDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}