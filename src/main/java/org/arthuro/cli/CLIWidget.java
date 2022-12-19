package org.arthuro.cli;

import java.util.Map;
import java.util.Scanner;

/**
 * Classe décrivant un "Widget" basique pour notre CLI
 * Un widget est une partie de notre interface remplissant une fonctionalité précise, elle correspond généralement
 * à un "niveau" de menu
 * <p>
 * Elle possède un attribut "prompts", qui contient le texte que le widget affichera, un attribut "userInputScanner",
 * un scanner qui permet de récupérer les données entrées par l'utilisateur, et une fonction "execute", qui implémente
 * la fonctionnalité principale du widget en question.
 */
public abstract class CLIWidget {
    //On peut éventuellement créer des classes abstraites plus ou moins spécialisées mais étant donné l'échelle
    //du projet cela semble exagéré.
    Map<String, String> prompts;
    Scanner userInputScanner;

    public CLIWidget(Scanner userInputScanner) {
        this.userInputScanner = userInputScanner;
    }

    public CLIWidget() {
    }

    public abstract void execute() throws Exception;
}
