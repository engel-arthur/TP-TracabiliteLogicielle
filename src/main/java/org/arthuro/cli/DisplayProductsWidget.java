package org.arthuro.cli;

import org.arthuro.app.ProductRepository;

import java.util.HashMap;

public class DisplayProductsWidget extends CLIWidget {
    ProductRepository productRepository;

    DisplayProductsWidget(ProductRepository productRepository) {
        userInputScanner = null;
        prompts = new HashMap<>() {{
            put("mainPrompt", "Voici la liste de tous les produits :");
        }};
        this.productRepository = productRepository;
    }

    public void execute() {
        System.out.println(prompts.get("mainPrompt"));
        System.out.println(productRepository);
    }
}
