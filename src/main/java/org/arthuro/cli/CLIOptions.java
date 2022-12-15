package org.arthuro.cli;

/**
 * Cette énumération contient les options disponibles pour l'utilisateur quand il utilise la CLI
 * Leur ordre détermine la touche que l'utilisateur doit presser pour les utiliser.
 */
public enum CLIOptions {
        quit,
        createUser,
        displayProducts,
        searchProductById,
        createProduct,
        deleteProductById,
        updateProduct,
}
