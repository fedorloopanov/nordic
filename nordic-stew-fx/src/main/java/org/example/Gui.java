package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Gui extends Application {

    private final ObservableList<String> orderHistory = FXCollections.observableArrayList();
    private final List<ToppingOption> toppingOptions = new ArrayList<>();

    private Label orderLabel;
    private Label priceLabel;
    private Label infoLabel;

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Заказ нордского рагу");
        Label baseDishLabel = new Label("Базовое блюдо: Нордское рагу (50 септимов)");

        orderLabel = new Label("Текущий заказ: Нордское рагу");
        priceLabel = new Label("Цена: 50 септимов");
        infoLabel = new Label("Можно выбрать не более 3 добавок");

        Button orderButton = new Button("Оформить заказ");
        Button clearButton = new Button("Сбросить");

        ListView<String> historyListView = new ListView<>(orderHistory);
        historyListView.setPrefHeight(180);

        createToppingOptions();

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        root.getChildren().add(titleLabel);
        root.getChildren().add(baseDishLabel);

        for (ToppingOption option : toppingOptions) {
            root.getChildren().add(option.getCheckBox());
            option.getCheckBox().setOnAction(e -> updateUI());
        }

        orderButton.setOnAction(e -> {
            Dish dish = buildDish();
            OrderRecord record = new OrderRecord(dish.getName(), dish.getPrice());
            orderHistory.add(record.toString());
            infoLabel.setText("Заказ добавлен в историю");
        });

        clearButton.setOnAction(e -> {
            for (ToppingOption option : toppingOptions) {
                option.getCheckBox().setSelected(false);
                option.getCheckBox().setDisable(false);
            }
            updateUI();
        });

        root.getChildren().addAll(
                orderLabel,
                priceLabel,
                infoLabel,
                orderButton,
                clearButton,
                new Label("История заказов:"),
                historyListView
        );

        Scene scene = new Scene(root, 430, 500);
        stage.setTitle("Гарцующая Кобыла");
        stage.setScene(scene);
        stage.show();
    }

    private void createToppingOptions() {
        toppingOptions.add(new ToppingOption(
                new CheckBox("Огненный соус (+10)"),
                FireSauceDecorator::new
        ));

        toppingOptions.add(new ToppingOption(
                new CheckBox("Двойная порция оленины (+20)"),
                DoubleVenisonDecorator::new
        ));

        toppingOptions.add(new ToppingOption(
                new CheckBox("Свежие ягоды (+6)"),
                BerriesDecorator::new
        ));

        toppingOptions.add(new ToppingOption(
                new CheckBox("Нордская лепешка (+7)"),
                FlatbreadDecorator::new
        ));
    }

    private void updateUI() {
        enforceToppingLimit();

        Dish dish = buildDish();
        orderLabel.setText("Текущий заказ: " + dish.getName());
        priceLabel.setText("Цена: " + dish.getPrice() + " септимов");

        if (countSelected() >= 3) {
            infoLabel.setText("Достигнут максимум: 3 добавки");
        } else {
            infoLabel.setText("Можно выбрать не более 3 добавок");
        }
    }

    private void enforceToppingLimit() {
        boolean limitReached = countSelected() >= 3;

        for (ToppingOption option : toppingOptions) {
            CheckBox checkBox = option.getCheckBox();
            if (!checkBox.isSelected()) {
                checkBox.setDisable(limitReached);
            } else {
                checkBox.setDisable(false);
            }
        }
    }

    private int countSelected() {
        int count = 0;
        for (ToppingOption option : toppingOptions) {
            if (option.getCheckBox().isSelected()) {
                count++;
            }
        }
        return count;
    }

    private Dish buildDish() {
        Dish dish = new NordicRagout();

        for (ToppingOption option : toppingOptions) {
            if (option.getCheckBox().isSelected()) {
                dish = option.applyDecorator(dish);
            }
        }

        return dish;
    }

    private static class ToppingOption {
        private final CheckBox checkBox;
        private final Function<Dish, Dish> decorator;

        public ToppingOption(CheckBox checkBox, Function<Dish, Dish> decorator) {
            this.checkBox = checkBox;
            this.decorator = decorator;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public Dish applyDecorator(Dish dish) {
            return decorator.apply(dish);
        }
    }
}
