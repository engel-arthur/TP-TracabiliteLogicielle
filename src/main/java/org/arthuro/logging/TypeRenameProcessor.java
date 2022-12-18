package org.arthuro.logging;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtType;

public class TypeRenameProcessor extends AbstractProcessor<CtType> {

    @Override
    public void process(CtType element) {
        if (!element.getSimpleName().equals(""))
            element.setSimpleName(element.getSimpleName() + "Spoon");
    }
}
