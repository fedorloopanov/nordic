package org.example;

public class FireSauceDecorator extends DishDecorator {
    public FireSauceDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return dish.getName() + " + Огненный соус";
    }

    @Override
    public int getPrice() {
        return dish.getPrice() + 10;
    }
}
