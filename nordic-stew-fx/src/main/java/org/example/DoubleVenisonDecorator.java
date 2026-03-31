package org.example;

public class DoubleVenisonDecorator extends DishDecorator {

    public DoubleVenisonDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return getDish().getName() + " + Двойная порция оленины";
    }

    @Override
    public int getPrice() {
        return getDish().getPrice() + 20;
    }
}
