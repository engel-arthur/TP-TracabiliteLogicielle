package org.arthuro.logging;

import spoon.processing.AbstractProcessor;
import spoon.reflect.reference.CtTypeReference;

public class TypeReferenceRenameProcessor extends AbstractProcessor<CtTypeReference> {

    @Override
    public void process(CtTypeReference element) {
        if (element.getDeclaration() != null)
            element.setSimpleName(element.getSimpleName() + "Spoon");
    }
}
