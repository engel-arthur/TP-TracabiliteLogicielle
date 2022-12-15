package org.arthuro.app;

import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.exception.ProductNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private Map<Integer, Product> products;

    public ProductRepository() {
        products = new HashMap<>();
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        if (products.containsKey(id))
            return products.get(id);
        else
            throw new ProductNotFoundException("Product doesn't exist");
    }

    public void addProduct(Product product) throws ProductAlreadyExistsException {
        if (products.containsKey(product.getId()))
            throw new ProductAlreadyExistsException("Product already exists");
        else
            products.put(product.getId(), product);
    }

    public void deleteProductById(int id) throws ProductNotFoundException {
        if (products.containsKey(id))
            products.remove(id);
        else
            throw new ProductNotFoundException("Product doesn't exist");
    }

    public void updateProduct(int id, String newName, int newPrice, LocalDate newExpirationDate)
            throws ProductNotFoundException, InvalidExpirationDateException, InvalidPriceException {
        Product productToUpdate = getProductById(id);
        productToUpdate.setName(newName);
        productToUpdate.setPrice(newPrice);
        productToUpdate.setExpirationDate(newExpirationDate);
    }

    @Override
    public String toString() {
        return products + "\n";
    }
}
