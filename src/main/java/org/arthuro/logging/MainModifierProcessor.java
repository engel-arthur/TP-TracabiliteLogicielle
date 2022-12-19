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
                    }""");
            element.setBody(snippet);
        }
    }
}
