package org.arthuro.main;

import org.arthuro.cli.CLI;
import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.exception.ProductNotFoundException;

public final class Main {
    public static void main(String[] args) throws InvalidPriceException, ProductAlreadyExistsException, InvalidExpirationDateException, ProductNotFoundException {
        CLI.userInterface();
    }
}