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
public final class MainWidget extends CLIWidget {
    private final ProductRepository productRepository;
    private final List<User> users;

    private final Map<CLIOptions, CLIWidget> widgets;

    public MainWidget() {
        userInputScanner = new Scanner(System.in);
        prompts = new HashMap<>() {{
            put("mainPrompt", """
                    Choissisez une option parmi celles de la liste (entrez le numéro correspondant et appuyez sur entrée)
                    1. Créer un utilisateur
                    2. Afficher tous les produits
                    3. Rechercher un produit par son ID
                    4. Ajouter un nouveau produit
                    5. Supprimer un produit par son ID
                    6. Mettre à jour un produit
                    0. Quitter""");
        }};
        productRepository = new ProductRepository();
        users = new ArrayList<>();
        widgets = new HashMap<>() {{
            put(CLIOptions.createUser, new CreateUserWidget(userInputScanner, users));
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

            System.out.println(prompts.get("mainPrompt"));

            userChoice = CLIOptionsValues[userInputScanner.nextInt()];

            if (userChoice != CLIOptions.quit) {
                widgets.get(userChoice).execute();
            }

            String promptSeparator = "\n================================\n";
            System.out.println(promptSeparator);
        } while (userChoice != CLIOptions.quit);

        userInputScanner.close();
    }
}
