package org.example;

public class FireSauceDecorator extends DishDecorator {

    public FireSauceDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return getDish().getName() + " + Огненный соус";
    }

    @Override
    public int getPrice() {
        return getDish().getPrice() + 10;
    }
}
