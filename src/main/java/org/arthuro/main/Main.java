package org.arthuro.main;

import org.apache.commons.io.FileUtils;
import org.arthuro.logging.LogParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {
    public static void main(String[] args) throws Exception {
        /*clearGeneratedFiles("./spooned/src/main/java/");

        SpoonParser spoonParser = new SpoonParser();
        spoonParser.parseApplicationWithSpoon();*/
        Path logFilePath = Paths.get("./tests.log");
        Path outputUsersFilePath = Paths.get("./users.json");
        Path outputProfilesFilePath = Paths.get("./profiles.json");
        LogParser logParser = new LogParser(logFilePath, outputUsersFilePath, outputProfilesFilePath);
        logParser.parse();
    }

    private static void clearGeneratedFiles(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        FileUtils.cleanDirectory(directory);
    }
}