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
                    org.arthuro.cli.MainWidget mainWidget = new org.arthuro.cli.MainWidget();
                    mainWidget.execute()""");
            element.setBody(snippet);
        }
    }
}
