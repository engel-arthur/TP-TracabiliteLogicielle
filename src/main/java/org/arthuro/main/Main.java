package org.arthuro.main;

import org.apache.commons.io.FileUtils;
import org.arthuro.app.Product;
import org.arthuro.app.ProductRepository;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.logging.SpoonParser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public final class Main {
    public static void main(String[] args) throws Exception {
        clearGeneratedFiles("./spooned/src/main/java/");

        SpoonParser spoonParser = new SpoonParser();
        spoonParser.parseApplicationWithSpoon();
    }

    private static void clearGeneratedFiles(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        FileUtils.cleanDirectory(directory);
    }
}