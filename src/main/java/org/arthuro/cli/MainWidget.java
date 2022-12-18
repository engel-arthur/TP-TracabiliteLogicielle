package org.arthuro.cli;

import org.arthuro.app.ProductRepository;
import org.arthuro.app.User;

import java.util.*;

public final class MainWidget extends CLIWidget {
    private ProductRepository productRepository;
    private List<User> users;

    private Map<CLIOptions, CLIWidget> widgets;

    private final String promptSeparator = "\n================================\n";

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

            switch (userChoice) {
                case displayProducts -> displayProducts();
                case quit -> {}
                default -> widgets.get(userChoice).execute();
            }

            System.out.println(promptSeparator);
        } while (userChoice != CLIOptions.quit);

        userInputScanner.close();
    }

    private void displayProducts() {
        System.out.println(productRepository);
    }
}
