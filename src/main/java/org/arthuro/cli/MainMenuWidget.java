package org.arthuro.cli;

import org.arthuro.app.ProductRepository;
import org.arthuro.app.User;

import java.util.*;

/**
 * Widget principal, qui sert de "hub" permettant d'accéder à tous les autres.
 * Il possède un ProductRepository et une liste de Users, qu'il fera passer aux widgets enfant
 * Il fera également passer son scanner aux widgets enfants (il vaut mieux éviter d'en créer plusieurs).
 * Il stocke les widgets accessible dans un attribut widgets dédié.
 */
public final class MainMenuWidget extends CLIWidget {
    private ProductRepository productRepository;
    private final User user;

    private final Map<CLIOptions, CLIWidget> widgets;

    public MainMenuWidget(Scanner userInputScanner, User user) {
        super(userInputScanner);
        this.user = user;
        prompts = new HashMap<>() {{
            put("mainPrompt", """
                    Choissisez une option parmi celles de la liste (entrez le numéro correspondant et appuyez sur entrée)
                    1. Afficher tous les produits
                    2. Rechercher un produit par son ID
                    3. Ajouter un nouveau produit
                    4. Supprimer un produit par son ID
                    5. Mettre à jour un produit
                    0. Quitter""");
        }};
        productRepository = new ProductRepository();
        widgets = new HashMap<>() {{
            put(CLIOptions.displayProducts, new DisplayProductsWidget(productRepository));
            put(CLIOptions.searchProductById, new SearchProductWidget(userInputScanner, productRepository));
            put(CLIOptions.createProduct, new CreateProductWidget(userInputScanner, productRepository));
            put(CLIOptions.deleteProductById, new DeleteProductWidget(userInputScanner, productRepository));
            put(CLIOptions.updateProduct, new UpdateProductWidget(userInputScanner, productRepository));
        }};
    }

    public void execute() throws Exception {
        CLIOptions userChoice;
        CLIOptions[] CLIOptionsValues = CLIOptions.values();

        do {
            userChoice = getUserChoice(CLIOptionsValues);

            handleUserChoice(userChoice);
        } while (userChoice != CLIOptions.quit);

        userInputScanner.close();
    }

    private void handleUserChoice(CLIOptions userChoice) throws Exception {
        if (userChoice != CLIOptions.quit) {
            widgets.get(userChoice).execute();
        }

        String promptSeparator = "\n================================\n";
        System.out.println(promptSeparator);
    }

    private CLIOptions getUserChoice(CLIOptions[] CLIOptionsValues) {
        CLIOptions userChoice;
        System.out.println(prompts.get("mainPrompt"));

        userChoice = CLIOptionsValues[userInputScanner.nextInt()];
        return userChoice;
    }
}
