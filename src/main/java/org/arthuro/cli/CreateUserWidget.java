package org.arthuro.cli;

import org.arthuro.app.User;

import java.util.HashMap;
import java.util.Scanner;

public class CreateUserWidget extends CLIWidget {
    User user;

    public CreateUserWidget(Scanner userInputScanner) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterName", "Entrez le nom de l'utilisateur : ");
            put("enterAge", "Entrez l'âge de l'utilisateur : ");
            put("enterMail", "Entrez l'email de l'utilisateur : ");
            put("enterPassword", "Entrez le mot de passe de l'utilisateur : ");
            put("confirmationText", "Utilisateur créé !");
        }};
    }

    @Override
    public void execute() throws Exception {
        System.out.println(prompts.get("enterName"));
        String name = userInputScanner.next();

        System.out.println(prompts.get("enterAge"));
        int age = userInputScanner.nextInt();

        System.out.println(prompts.get("enterMail"));
        String email = userInputScanner.next();

        System.out.println(prompts.get("enterPassword"));
        String password = userInputScanner.next();

        user = new User(name, age, email, password);

        System.out.println(prompts.get("confirmationText"));
        System.out.println(user);

        MainMenuWidget mainMenuWidget = new MainMenuWidget(userInputScanner, user);
        mainMenuWidget.execute();
    }
}
