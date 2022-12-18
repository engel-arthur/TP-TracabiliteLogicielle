package org.arthuro.logging;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;

public class LogProcessor extends AbstractProcessor<CtExecutable> {

    @Override
    public void process(CtExecutable element) {
        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();

        // Snippet which contains the log.
        final String value = String.format("""
                        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(%s.class);
                        logger.info("%s")""",
                element.getReference().getDeclaringType().getSimpleName(),
                element.getReference().getDeclaringType().getSimpleName());
        snippet.setValue(value);

        // Inserts the snippet at the beginning of the method body.
        if (element.getBody() != null && !element.getReference().isStatic()) {
            element.getBody().insertBegin(snippet);
        }
    }
}