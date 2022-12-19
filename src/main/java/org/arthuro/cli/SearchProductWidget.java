package org.arthuro.cli;

import org.arthuro.app.Product;
import org.arthuro.app.ProductRepository;
import org.arthuro.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Scanner;

public class SearchProductWidget extends CLIWidget {

    ProductRepository productRepository;

    public SearchProductWidget(Scanner userInputScanner, ProductRepository productRepository) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterId", "Entrez l'id du produit à retrouver : ");
            put("resultText", "Voici les informations du produit : ");
        }};
        this.productRepository = productRepository;
    }

    @Override
    public void execute() throws ProductNotFoundException {

        System.out.println(prompts.get("enterId"));

        int id = userInputScanner.nextInt();
        Product product = productRepository.getProductById(id);

        System.out.println(prompts.get("resultText"));
        System.out.println(product);
    }
}
