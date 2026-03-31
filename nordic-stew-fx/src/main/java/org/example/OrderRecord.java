package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OrderRecord {
    private final String time;
    private final String orderName;
    private final int price;

    public OrderRecord(String orderName, int price) {
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.orderName = orderName;
        this.price = price;
    }

    @Override
    public String toString() {
        return time + " | " + orderName + " | " + price + " септимов";
    }
}
