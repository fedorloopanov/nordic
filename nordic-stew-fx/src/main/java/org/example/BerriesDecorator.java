package org.example;

public class BerriesDecorator extends DishDecorator {
    public BerriesDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return dish.getName() + " + Свежие ягоды";
    }

    @Override
    public int getPrice() {
        return dish.getPrice() + 6;
    }
}
