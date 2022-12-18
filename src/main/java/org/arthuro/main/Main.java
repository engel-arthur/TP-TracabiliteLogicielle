package org.arthuro.main;

import org.apache.commons.io.FileUtils;
import org.arthuro.cli.MainWidget;
import org.arthuro.exception.InvalidExpirationDateException;
import org.arthuro.exception.InvalidPriceException;
import org.arthuro.exception.ProductAlreadyExistsException;
import org.arthuro.exception.ProductNotFoundException;
import org.arthuro.logging.SpoonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class Main {
    public static void main(String[] args) throws Exception {
        /*clearGeneratedFiles("./spooned/src/main/java/");

        SpoonParser spoonParser = new SpoonParser();
        spoonParser.parseApplicationWithSpoon();*/

        MainWidget mainWidget = new MainWidget();
        mainWidget.execute();
    }

    private static void clearGeneratedFiles(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        FileUtils.cleanDirectory(directory);
    }
}