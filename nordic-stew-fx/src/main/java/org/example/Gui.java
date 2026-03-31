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

public class Gui extends Application {

    private final ObservableList<String> orderHistory = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Заказ нордского рагу");
        Label baseDishLabel = new Label("Базовое блюдо: Нордское рагу (50 септимов)");

        CheckBox fireSauceBox = new CheckBox("Огненный соус (+10)");
        CheckBox doubleVenisonBox = new CheckBox("Двойная порция оленины (+20)");
        CheckBox berriesBox = new CheckBox("Свежие ягоды (+6)");
        CheckBox flatbreadBox = new CheckBox("Нордская лепешка (+7)");

        Label orderLabel = new Label("Текущий заказ: Нордское рагу");
        Label priceLabel = new Label("Цена: 50 септимов");
        Label infoLabel = new Label("Можно выбрать не более 3 добавок");

        Button orderButton = new Button("Оформить заказ");
        Button clearButton = new Button("Сбросить");

        ListView<String> historyListView = new ListView<>(orderHistory);
        historyListView.setPrefHeight(180);

        fireSauceBox.setOnAction(e -> updateCurrentOrder(
                fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox,
                orderLabel, priceLabel, infoLabel
        ));

        doubleVenisonBox.setOnAction(e -> updateCurrentOrder(
                fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox,
                orderLabel, priceLabel, infoLabel
        ));

        berriesBox.setOnAction(e -> updateCurrentOrder(
                fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox,
                orderLabel, priceLabel, infoLabel
        ));

        flatbreadBox.setOnAction(e -> updateCurrentOrder(
                fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox,
                orderLabel, priceLabel, infoLabel
        ));

        orderButton.setOnAction(e -> {
            Dish dish = buildDish(fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox);
            OrderRecord record = new OrderRecord(dish.getName(), dish.getPrice());
            orderHistory.add(record.toString());
            infoLabel.setText("Заказ добавлен в историю");
        });

        clearButton.setOnAction(e -> {
            fireSauceBox.setSelected(false);
            doubleVenisonBox.setSelected(false);
            berriesBox.setSelected(false);
            flatbreadBox.setSelected(false);

            fireSauceBox.setDisable(false);
            doubleVenisonBox.setDisable(false);
            berriesBox.setDisable(false);
            flatbreadBox.setDisable(false);

            orderLabel.setText("Текущий заказ: Нордское рагу");
            priceLabel.setText("Цена: 50 септимов");
            infoLabel.setText("Можно выбрать не более 3 добавок");
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                titleLabel,
                baseDishLabel,
                fireSauceBox,
                doubleVenisonBox,
                berriesBox,
                flatbreadBox,
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

    private void updateCurrentOrder(
            CheckBox fireSauceBox,
            CheckBox doubleVenisonBox,
            CheckBox berriesBox,
            CheckBox flatbreadBox,
            Label orderLabel,
            Label priceLabel,
            Label infoLabel
    ) {
        enforceToppingLimit(fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox);

        Dish dish = buildDish(fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox);
        orderLabel.setText("Текущий заказ: " + dish.getName());
        priceLabel.setText("Цена: " + dish.getPrice() + " септимов");

        int selectedCount = countSelected(fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox);
        if (selectedCount >= 3) {
            infoLabel.setText("Достигнут максимум: 3 добавки");
        } else {
            infoLabel.setText("Можно выбрать не более 3 добавок");
        }
    }

    private void enforceToppingLimit(
            CheckBox fireSauceBox,
            CheckBox doubleVenisonBox,
            CheckBox berriesBox,
            CheckBox flatbreadBox
    ) {
        int selectedCount = countSelected(fireSauceBox, doubleVenisonBox, berriesBox, flatbreadBox);
        boolean limitReached = selectedCount >= 3;

        if (!fireSauceBox.isSelected()) {
            fireSauceBox.setDisable(limitReached);
        }
        if (!doubleVenisonBox.isSelected()) {
            doubleVenisonBox.setDisable(limitReached);
        }
        if (!berriesBox.isSelected()) {
            berriesBox.setDisable(limitReached);
        }
        if (!flatbreadBox.isSelected()) {
            flatbreadBox.setDisable(limitReached);
        }
    }

    private int countSelected(CheckBox... boxes) {
        int count = 0;
        for (CheckBox box : boxes) {
            if (box.isSelected()) {
                count++;
            }
        }
        return count;
    }

    private Dish buildDish(
            CheckBox fireSauceBox,
            CheckBox doubleVenisonBox,
            CheckBox berriesBox,
            CheckBox flatbreadBox
    ) {
        Dish dish = new NordicRagout();

        if (fireSauceBox.isSelected()) {
            dish = new FireSauceDecorator(dish);
        }
        if (doubleVenisonBox.isSelected()) {
            dish = new DoubleVenisonDecorator(dish);
        }
        if (berriesBox.isSelected()) {
            dish = new BerriesDecorator(dish);
        }
        if (flatbreadBox.isSelected()) {
            dish = new FlatbreadDecorator(dish);
        }

        return dish;
    }
}
