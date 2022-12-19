package org.arthuro.app;

import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;

import java.time.LocalDate;

public class Product {
    private final int id;
    private String name;
    private int price;
    private LocalDate expirationDate;

    public Product(int id, String name, int price, LocalDate expirationDate) throws InvalidExpirationDateException, InvalidPriceException {
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

    public void setPrice(int price) throws InvalidPriceException {
        if (price > 0)
            this.price = price;
        else
            throw new InvalidPriceException("Invalid price! " + price + " should be superior to 0.");
    }

    public void setExpirationDate(LocalDate expirationDate) throws InvalidExpirationDateException {
        if (expirationDate.isAfter(LocalDate.now()))
            this.expirationDate = expirationDate;
        else
            throw new InvalidExpirationDateException("Invalid date! " + expirationDate
                    + " should be an ulterior date from today ( " + LocalDate.now() + " ).");
    }

    @Override
    public String toString() {
        return "{ " +
                "name='" + name + '\'' +
                ", price=" + price +
                ", expirationDate=" + expirationDate +
                " }\n";
    }
}
