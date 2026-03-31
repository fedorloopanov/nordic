package org.example;

public class DoubleVenisonDecorator extends DishDecorator {
    public DoubleVenisonDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return dish.getName() + " + Двойная порция оленины";
    }

    @Override
    public int getPrice() {
        return dish.getPrice() + 20;
    }
}
