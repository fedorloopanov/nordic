package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

    private final ObservableList<String> history = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Label title = new Label("Оформление заказа");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label baseDishLabel = new Label("Базовое блюдо: Нордское рагу (50 септимов)");

        CheckBox fireSauceBox = new CheckBox("Огненный соус (+10)");
        CheckBox venisonBox = new CheckBox("Двойная порция оленины (+20)");
        CheckBox berriesBox = new CheckBox("Свежие ягоды (+6)");
        CheckBox flatbreadBox = new CheckBox("Нордская лепешка (+7)");

        Label infoLabel = new Label("Можно выбрать не более 3 добавок");
        Label currentOrderLabel = new Label();
        Label currentPriceLabel = new Label();

        Button makeOrderButton = new Button("Оформить заказ");
        Button clearButton = new Button("Сбросить");

        ListView<String> historyList = new ListView<>(history);
        historyList.setPrefHeight(200);

        Runnable updater = () -> {
            int selected = countSelected(fireSauceBox, venisonBox, berriesBox, flatbreadBox);
            boolean limitReached = selected >= 3;

            if (!fireSauceBox.isSelected()) fireSauceBox.setDisable(limitReached);
            if (!venisonBox.isSelected()) venisonBox.setDisable(limitReached);
            if (!berriesBox.isSelected()) berriesBox.setDisable(limitReached);
            if (!flatbreadBox.isSelected()) flatbreadBox.setDisable(limitReached);

            Dish dish = buildDish(fireSauceBox, venisonBox, berriesBox, flatbreadBox);
            currentOrderLabel.setText("Текущий заказ: " + dish.getName());
            currentPriceLabel.setText("Цена: " + dish.getPrice() + " септимов");
        };

        fireSauceBox.setOnAction(e -> updater.run());
        venisonBox.setOnAction(e -> updater.run());
        berriesBox.setOnAction(e -> updater.run());
        flatbreadBox.setOnAction(e -> updater.run());

        makeOrderButton.setOnAction(e -> {
            Dish dish = buildDish(fireSauceBox, venisonBox, berriesBox, flatbreadBox);
            history.add(new OrderRecord(dish.getName(), dish.getPrice()).toString());
            infoLabel.setText("Заказ добавлен в свиток заказов");
        });

        clearButton.setOnAction(e -> {
            fireSauceBox.setSelected(false);
            venisonBox.setSelected(false);
            berriesBox.setSelected(false);
            flatbreadBox.setSelected(false);

            fireSauceBox.setDisable(false);
            venisonBox.setDisable(false);
            berriesBox.setDisable(false);
            flatbreadBox.setDisable(false);

            infoLabel.setText("Можно выбрать не более 3 добавок");
            updater.run();
        });

        updater.run();

        HBox buttonBox = new HBox(10, makeOrderButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(12,
                title,
                baseDishLabel,
                new Label("Добавки:"),
                fireSauceBox,
                venisonBox,
                berriesBox,
                flatbreadBox,
                currentOrderLabel,
                currentPriceLabel,
                buttonBox,
                infoLabel,
                new Label("Свиток заказов:"),
                historyList
        );

        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 520, 500);
        stage.setTitle("Гарцующая Кобыла");
        stage.setScene(scene);
        stage.show();
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

    private Dish buildDish(CheckBox fireSauceBox, CheckBox venisonBox, CheckBox berriesBox, CheckBox flatbreadBox) {
        Dish dish = new NordicRagout();

        if (fireSauceBox.isSelected()) {
            dish = new FireSauceDecorator(dish);
        }
        if (venisonBox.isSelected()) {
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
