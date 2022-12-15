package org.arthuro.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe contient tous les textes utilisés par l'interface
 * Les textes appartenant à un même groupement logique sont stockés sous forme de collection
 * Une liste est utilisée si le texte est fait pour être affiché en un bloc
 * Une map utilisant des clés indiauant le rôle du texte est utilisée pour les groupes devant être affichés
 * les uns après les autres.
 */

public final class CLIStrings {
    private static final String userChoicePrompt =
            "Choissisez une option parmi celles de la liste (entrez le numéro correspondant et appuyez sur entrée)";

    private static final List<String> optionsList = new ArrayList<>() {{
        add("1. Créer un utilisateur");
        add("2. Afficher tous les produits");
        add("3. Rechercher un produit par son ID");
        add("4. Ajouter un nouveau produit");
        add("5. Supprimer un produit par son ID");
        add("6. Mettre à jour un produit");
        add("0. Quitter");
    }};

    private static final Map<String, String> createUserPrompts = new HashMap<>() {{
        put("enterName", "Entrez le nom de l'utilisateur : ");
        put("enterAge", "Entrez l'âge de l'utilisateur : ");
        put("enterMail", "Entrez l'email de l'utilisateur : ");
        put("enterPassword", "Entrez le mot de passe de l'utilisateur : ");
        put("confirmationText", "Utilisateur créé !");
    }};

    private static final Map<String,String> searchProductByIdPrompts = new HashMap<>(){{
        put("enterId", "Entrez l'id du produit à retrouver : ");
        put("resultText", "Voici les informations du produit : ");
    }};

    private static final Map<String, String> createProductPrompts = new HashMap<>() {{
        put("enterId","Entrez l'id du produit à ajouter :");
        put("enterName","Entrez le nom du produit à ajouter : ");
        put("enterPrice","Entrez le prix du produit à ajouter : ");
        put("enterDate","Entrez la date d'expiration du produit (\"aaaa-mm-jj\") à ajouter : ");
        put("confirmationText", "Produit créé !");
    }};

    private static final Map<String, String> deleteProductByIdPrompts = new HashMap<>() {{
        put("enterId", "Entrez l'id du produit à supprimer : ");
        put("confirmationText", "Produit supprimé.");
    }};

    private static final Map<String, String> updateProductByIdPrompts = new HashMap<>() {{
        put("enterId", "Entrez l'id du produit à mettre à jour : ");
        put("productInfo","Voici le produit que vous voulez mettre à jour : ");
        put("enterName","Entrez le nouveau nom du produit : ");
        put("enterPrice","Entrez le nouveau prix du produit : ");
        put("enterDate","Entrez la nouvelle date d'expiration du produit (\"aaaa-mm-jj\") : ");
        put("confirmationText", "Produit mis à jour !");
    }};

    private static final String separator = "\n================================================================\n";

    private CLIStrings(){}

    public static String getUserChoicePrompt() {
        return userChoicePrompt;
    }

    public static List<String> getOptionsList() {
        return optionsList;
    }

    public static Map<String, String> getCreateUserPrompts() {
        return createUserPrompts;
    }

    public static Map<String, String> getCreateProductPrompts() {
        return createProductPrompts;
    }

    public static Map<String, String> getUpdateProductByIdPrompts() {
        return updateProductByIdPrompts;
    }

    public static Map<String, String> getSearchProductByIdPrompts() {
        return searchProductByIdPrompts;
    }

    public static Map<String, String> getDeleteProductByIdPrompts() {
        return deleteProductByIdPrompts;
    }

    public static String getSeparator() {
        return separator;
    }
}
