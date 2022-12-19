package org.arthuro.logging;

import org.eclipse.jdt.internal.codeassist.select.SelectionOnLambdaExpression;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;

public class LogProcessor extends AbstractProcessor<CtExecutable> {

    @Override
    public void process(CtExecutable element) {
        CtCodeSnippetStatement userOperationsSnippet = getFactory().Core().createCodeSnippetStatement();
        CtCodeSnippetStatement productPriceSnippet = getFactory().Core().createCodeSnippetStatement();

        // Snippet which contains the log.
        final String userOperationValue = String.format("""
                        final String userInfoJSONFormat = "\\"user\\" : [" + user.getId() + ", \\"" + user.getName() + "\\", " +
                                        user.getAge() + ", \\"" + user.getEmail() + "\\"]," + " \\"op\\" :";
                        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(%s.class);
                        if ((userChoice == org.arthuro.cli.CLIOptions.displayProducts) || (userChoice == org.arthuro.cli.CLIOptions.searchProductById))
                        logger.info(userInfoJSONFormat + " \\"READ\\"");
                        else if(userChoice != org.arthuro.cli.CLIOptions.quit)
                        logger.info(userInfoJSONFormat + " \\"WRITE\\"")
                        """,
                element.getReference().getDeclaringType().getSimpleName());
        userOperationsSnippet.setValue(userOperationValue);

        final String productPriceValue = String.format("""
                org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(%s.class);   
                logger.info("\\"price\\" : " + product.getPrice())
                """,
                element.getReference().getDeclaringType().getSimpleName());
        productPriceSnippet.setValue(productPriceValue);

        // Inserts the userOperationsSnippet at the beginning of the method body.
        if (element.getBody() != null && !element.getReference().isStatic()) {

            if (element.getReference().getDeclaringType().getSimpleName().equals("MainMenuWidget")) {

                if (element.getReference().getDeclaration().getSimpleName().equals("handleUserChoice")) {

                    element.getBody().insertEnd(userOperationsSnippet);
                }
            }

            if (element.getReference().getDeclaringType().getSimpleName().equals("SearchProductWidget")) {

                if (element.getReference().getDeclaration().getSimpleName().equals("execute")) {

                    element.getBody().insertEnd(productPriceSnippet);
                }
            }
        }
    }
}