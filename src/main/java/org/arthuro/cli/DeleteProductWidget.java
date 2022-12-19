package org.arthuro.cli;

import org.arthuro.app.ProductRepository;
import org.arthuro.exception.ProductNotFoundException;

import java.util.HashMap;
import java.util.Scanner;

public class DeleteProductWidget extends CLIWidget {

    ProductRepository productRepository;

    public DeleteProductWidget(Scanner userInputScanner, ProductRepository productRepository) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterId", "Entrez l'id du produit à supprimer : ");
            put("confirmationText", "Produit supprimé.");
        }};
        this.productRepository = productRepository;
    }

    @Override
    public void execute() throws ProductNotFoundException {

        System.out.println(prompts.get("enterId"));
        int id = userInputScanner.nextInt();

        productRepository.deleteProductById(id);

        System.out.println(prompts.get("confirmationText"));
    }
}
