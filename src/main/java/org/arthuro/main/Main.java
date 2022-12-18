package org.arthuro.main;

import org.arthuro.cli.CLI;
import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.exception.ProductNotFoundException;
import org.arthuro.logging.LogProcessor;
import org.arthuro.logging.SpoonParser;

public final class Main {
    public static void main(String[] args) throws InvalidPriceException, ProductAlreadyExistsException, InvalidExpirationDateException, ProductNotFoundException {
        SpoonParser spoonParser = new SpoonParser();
        spoonParser.parseApplicationWithSpoon();

        CLI.userInterface();
    }
}