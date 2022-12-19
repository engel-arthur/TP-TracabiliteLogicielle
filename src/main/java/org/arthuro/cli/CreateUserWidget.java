package org.arthuro.cli;

import org.arthuro.app.User;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CreateUserWidget extends CLIWidget {
    List<User> users;

    public CreateUserWidget(Scanner userInputScanner, List<User> users) {
        super(userInputScanner);
        prompts = new HashMap<>() {{
            put("enterName", "Entrez le nom de l'utilisateur : ");
            put("enterAge", "Entrez l'âge de l'utilisateur : ");
            put("enterMail", "Entrez l'email de l'utilisateur : ");
            put("enterPassword", "Entrez le mot de passe de l'utilisateur : ");
            put("confirmationText", "Utilisateur créé !");
        }};
        this.users = users;
    }

    @Override
    public void execute() {
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
}
