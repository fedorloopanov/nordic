package org.example;

public class BerriesDecorator extends DishDecorator {

    public BerriesDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return getDish().getName() + " + Свежие ягоды";
    }

    @Override
    public int getPrice() {
        return getDish().getPrice() + 6;
    }
}
