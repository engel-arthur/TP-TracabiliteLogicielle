package org.arthuro.main;

import org.apache.commons.io.FileUtils;
import org.arthuro.cli.MainWidget;

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