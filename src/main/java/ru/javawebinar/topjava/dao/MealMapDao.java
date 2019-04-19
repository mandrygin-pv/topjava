package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMapDao implements MealDao {
    private final AtomicInteger id = new AtomicInteger(0);
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    {
        for (Meal meal : MealsUtil.getMealList()) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(id.incrementAndGet());
        return storage.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        return storage.get(id);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}