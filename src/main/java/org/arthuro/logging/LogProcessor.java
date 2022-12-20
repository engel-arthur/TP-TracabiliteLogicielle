package org.arthuro.logging;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;

import java.util.Iterator;

public class LogProcessor extends AbstractProcessor<CtExecutable> {

    @Override
    public void process(CtExecutable element) {
        CtCodeSnippetStatement userOperationsSnippet = getFactory().Core().createCodeSnippetStatement();
        CtCodeSnippetStatement productPriceSnippet = getFactory().Core().createCodeSnippetStatement();

        // Snippet which contains the log.
        initializeUserOperationsSnippet(element, userOperationsSnippet);

        initializeProductPriceSnippet(element, productPriceSnippet);

        // Inserts the userOperationsSnippet at the beginning of the method body.
        if (element.getBody() != null && !element.getReference().isStatic()) {

            insertSnippetAtBeginningOfMethod(element, "MainMenuWidget", "handleUserChoice", userOperationsSnippet);

            insertSnippetAtEndOfMethod(element, "SearchProductWidget", "execute", productPriceSnippet);
        }
    }

    private static void initializeProductPriceSnippet(CtExecutable element, CtCodeSnippetStatement productPriceSnippet) {
        final String productPriceValue = String.format("""
                        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(%s.class);
                        logger.info("{\\"price\\" : " + product.getPrice() + "}")
                        """,
                element.getReference().getDeclaringType().getSimpleName());
        productPriceSnippet.setValue(productPriceValue);
    }

    private static void initializeUserOperationsSnippet(CtExecutable element, CtCodeSnippetStatement userOperationsSnippet) {
        final String userOperationValue = String.format("""
                        final String userInfoJSONFormat = "{\\"user\\" : [" + user.getId() + ", \\"" + user.getName() + "\\", " +
                                        user.getAge() + ", \\"" + user.getEmail() + "\\"]," + " \\"op\\" :";
                        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(%s.class);
                        if ((userChoice == org.arthuro.cli.CLIOptions.displayProducts) || (userChoice == org.arthuro.cli.CLIOptions.searchProductById))
                        logger.info(userInfoJSONFormat + " \\"READ\\"}");
                        else if(userChoice != org.arthuro.cli.CLIOptions.quit)
                        logger.info(userInfoJSONFormat + " \\"WRITE\\"}")
                        """,
                element.getReference().getDeclaringType().getSimpleName());
        userOperationsSnippet.setValue(userOperationValue);
    }

    private static void insertSnippetAtEndOfMethod(CtExecutable element, String className, String methodName, CtCodeSnippetStatement snippet) {
        if (element.getReference().getDeclaringType().getSimpleName().equals(className)) {

            if (element.getReference().getDeclaration().getSimpleName().equals(methodName)) {

                element.getBody().insertEnd(snippet);
            }
        }
    }

    private static void insertSnippetAtBeginningOfMethod(CtExecutable element, String className, String methodName, CtCodeSnippetStatement snippet) {
        if (element.getReference().getDeclaringType().getSimpleName().equals(className)) {

            if (element.getReference().getDeclaration().getSimpleName().equals(methodName)) {

                element.getBody().insertBegin(snippet);
            }
        }
    }
}