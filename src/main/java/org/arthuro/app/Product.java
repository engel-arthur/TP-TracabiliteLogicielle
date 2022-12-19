package org.arthuro.app;

import java.time.LocalDate;

public class Product {
    private final int id;
    private String name;
    private int price;
    private LocalDate expirationDate;

    public Product(int id, String name, int price, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        setPrice(price);
        setExpirationDate(expirationDate);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price > 0)
            this.price = price;
        else
            this.price = 1;
    }

    public void setExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expirationDate=" + expirationDate +
                " }\n";
    }
}
