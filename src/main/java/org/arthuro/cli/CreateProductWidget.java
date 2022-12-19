package org.arthuro.cli;

import org.arthuro.app.Product;
import org.arthuro.app.ProductRepository;
import org.arthuro.exception.ProductAlreadyExistsException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class CreateProductWidget extends CLIWidget {
    private ProductRepository productRepository;

    public CreateProductWidget(Scanner userInputScanner, ProductRepository productRepository) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterId", "Entrez l'id du produit à ajouter :");
            put("enterName", "Entrez le nom du produit à ajouter : ");
            put("enterPrice", "Entrez le prix du produit à ajouter : ");
            put("enterDate", "Entrez la date d'expiration du produit (\"aaaa-mm-jj\") à ajouter : ");
            put("confirmationText", "Produit créé !");
        }};
        this.productRepository = productRepository;
    }

    @Override
    public void execute() throws ProductAlreadyExistsException {

        System.out.println(prompts.get("enterId"));
        int id = userInputScanner.nextInt();

        System.out.println(prompts.get("enterName"));
        String name = userInputScanner.next();

        System.out.println(prompts.get("enterPrice"));
        int price = userInputScanner.nextInt();

        System.out.println(prompts.get("enterDate"));
        String dateText = userInputScanner.next();
        LocalDate date = LocalDate.parse(dateText);

        Product product = new Product(id, name, price, date);
        productRepository.addProduct(product);

        System.out.println(prompts.get("confirmationText"));
        System.out.println(product);
    }
}
