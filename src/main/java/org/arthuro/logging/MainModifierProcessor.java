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
                    org.arthuro.cli.CreateUserWidget createUserWidget = new org.arthuro.cli.CreateUserWidget(new java.util.Scanner(System.in));
                    createUserWidget.execute()""");
            element.setBody(snippet);
        }
    }
}
