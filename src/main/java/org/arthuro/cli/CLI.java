package org.arthuro.cli;

import org.arthuro.app.Product;
import org.arthuro.app.ProductRepository;
import org.arthuro.app.User;
import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.exception.ProductNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TODO séparer le prompting et la business logic ?
//TODO externaliser le fait de récupérer l'input utilisateur dans une méthode vérifiant l'input (getIntFromUser, getDateFromUser...)
public final class CLI {
    private final static ProductRepository productRepository = new ProductRepository();
    private final static List<User> users = new ArrayList<>();
    private final static Scanner userInputScanner = new Scanner(System.in);

    private CLI() {
    }

    public static void userInterface() throws ProductNotFoundException, ProductAlreadyExistsException, InvalidPriceException, InvalidExpirationDateException {
        CLIOptions userChoice;
        CLIOptions[] CLIOptionsValues = CLIOptions.values();

        Scanner scanner = new Scanner(System.in);

        do {

            System.out.println(CLIStrings.getUserChoicePrompt());
            displayWholePrompt(CLIStrings.getOptionsList());

            userChoice = CLIOptionsValues[scanner.nextInt()];

            switch (userChoice) {
                case createUser -> createUser();
                case displayProducts -> displayProducts();
                case searchProductById -> searchProductById();
                case createProduct -> createProduct();
                case deleteProductById -> deleteProductById();
                case updateProduct -> updateProduct();
            }

            System.out.println(CLIStrings.getSeparator());
        } while (userChoice != CLIOptions.quit);

        scanner.close();
    }

    private static void updateProduct() throws ProductNotFoundException, InvalidPriceException, InvalidExpirationDateException {
        Map<String, String> prompts = CLIStrings.getUpdateProductByIdPrompts();

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

    private static void deleteProductById() throws ProductNotFoundException {
        Map<String, String> prompts = CLIStrings.getDeleteProductByIdPrompts();

        System.out.println(prompts.get("enterId"));
        int id = userInputScanner.nextInt();

        productRepository.deleteProductById(id);

        System.out.println(prompts.get("confirmationText"));
    }

    private static void createProduct() throws ProductAlreadyExistsException, InvalidPriceException, InvalidExpirationDateException {
        Map<String, String> prompts = CLIStrings.getCreateProductPrompts();

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

    private static void searchProductById() throws ProductNotFoundException {
        Map<String, String> prompts = CLIStrings.getSearchProductByIdPrompts();

        System.out.println(prompts.get("enterId"));

        int id = userInputScanner.nextInt();
        Product product = productRepository.getProductById(id);

        System.out.println(prompts.get("resultText"));
        System.out.println(product);
    }

    private static void displayProducts() {
        System.out.println(productRepository);
    }

    private static void createUser() {
        Map<String, String> prompts = CLIStrings.getCreateUserPrompts();

        System.out.println(prompts.get("enterName"));
        String name = userInputScanner.next();

        System.out.println(prompts.get("enterAge"));
        int age = userInputScanner.nextInt();

        System.out.println(prompts.get("enterMail"));
        String email = userInputScanner.next();

        System.out.println(prompts.get("enterPassword"));
        String password = userInputScanner.next();

        User user = new User(name, age, email, password);
        users.add(user);

        System.out.println(prompts.get("confirmationText"));
        System.out.println(user);

    }

    private static void displayWholePrompt(List<String> prompts) {
        for (String prompt : prompts) {
            System.out.println(prompt);
        }
    }
}
