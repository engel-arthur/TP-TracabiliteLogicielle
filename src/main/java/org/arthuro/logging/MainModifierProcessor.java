package org.arthuro.logging;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtMethod;

public class MainModifierProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod element) {
        if (element.getSimpleName().equals("main")) {
            CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();

            snippet.setValue("""
                    java.io.File executionScenariosFolder = new java.io.File("./spooned/execution_scenarios");
                    for(java.io.File f : executionScenariosFolder.listFiles()) {
                        java.util.Scanner executionScenariosScanner = new java.util.Scanner(f);
                        org.arthuro.cli.CreateUserWidget createUserWidget = new org.arthuro.cli.CreateUserWidget(executionScenariosScanner);
                        createUserWidget.execute();
                    }
                    java.nio.file.Path logFilePath = java.nio.file.Paths.get("./tests.log");
                    java.nio.file.Path outputUsersFilePath = java.nio.file.Paths.get("./users.json");
                    java.nio.file.Path outputProfilesFilePath = java.nio.file.Paths.get("./profiles.json");
                    org.arthuro.logging.LogParser logParser = new org.arthuro.logging.LogParser(logFilePath, outputUsersFilePath, outputProfilesFilePath);
                    logParser.parse()""");
            element.setBody(snippet);
        }
    }
}
