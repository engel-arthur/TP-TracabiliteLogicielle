package org.arthuro.cli;

import org.arthuro.app.ProductRepository;
import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdateProductWidget extends CLIWidget{
    ProductRepository productRepository;

    public UpdateProductWidget(Scanner userInputScanner, ProductRepository productRepository) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterId", "Entrez l'id du produit à mettre à jour : ");
            put("productInfo","Voici le produit que vous voulez mettre à jour : ");
            put("enterName","Entrez le nouveau nom du produit : ");
            put("enterPrice","Entrez le nouveau prix du produit : ");
            put("enterDate","Entrez la nouvelle date d'expiration du produit (\"aaaa-mm-jj\") : ");
            put("confirmationText", "Produit mis à jour !");
        }};
        this.productRepository = productRepository;
    }

    @Override
    public void execute() throws ProductNotFoundException, InvalidPriceException, InvalidExpirationDateException {

        System.out.println(prompts.get("enterId"));
        int id = userInputScanner.nextInt();

        System.out.println(prompts.get("productInfo"));
        System.out.println(productRepository.getProductById(id));

        System.out.println(prompts.get("enterName"));
        String name = userInputScanner.next();

        System.out.println(prompts.get("enterPrice"));
        int price = userInputScanner.nextInt();

        System.out.println(prompts.get("enterDate"));
        String dateText = userInputScanner.next();

        LocalDate date = LocalDate.parse(dateText);

        productRepository.updateProduct(id, name, price, date);

        System.out.println(prompts.get("confirmationText"));
        System.out.println(productRepository.getProductById(id));
    }
}
