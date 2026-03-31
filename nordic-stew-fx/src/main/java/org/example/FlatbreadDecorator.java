package org.example;

public class FlatbreadDecorator extends DishDecorator {
    public FlatbreadDecorator(Dish dish) {
        super(dish);
    }

    @Override
    public String getName() {
        return dish.getName() + " + Нордская лепешка";
    }

    @Override
    public int getPrice() {
        return dish.getPrice() + 7;
    }
}
