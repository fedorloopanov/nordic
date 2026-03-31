package org.example;

public class NordicRagout implements Dish {
    @Override
    public String getName() {
        return "Нордское рагу";
    }

    @Override
    public int getPrice() {
        return 50;
    }
}
