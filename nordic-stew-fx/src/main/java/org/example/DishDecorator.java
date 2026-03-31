package org.example;

public abstract class DishDecorator implements Dish {

    private Dish dish;

    public DishDecorator(Dish dish) {
        this.dish = dish;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String getName() {
        return dish.getName();
    }

    @Override
    public int getPrice() {
        return dish.getPrice();
    }
}
