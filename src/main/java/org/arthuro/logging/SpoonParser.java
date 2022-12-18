package org.arthuro.logging;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;

import java.util.ArrayList;
import java.util.List;

public class SpoonParser {

    public void parseApplicationWithSpoon() {
        Launcher mainModifierLauncher = new Launcher();
        MainModifierProcessor mainModifierProcessor = new MainModifierProcessor();


        mainModifierLauncher.addInputResource("./src/main/java");
        mainModifierLauncher.setSourceOutputDirectory("./spooned/src/main/java");
        mainModifierLauncher.addProcessor(mainModifierProcessor);

        mainModifierLauncher.run();

        Launcher logProcessorLauncher = new Launcher();
        LogProcessor logProcessor = new LogProcessor();

        logProcessorLauncher.addInputResource("./src/main/java/org/arthuro/app");
        logProcessorLauncher.setSourceOutputDirectory("./spooned/src/main/java");
        logProcessorLauncher.addProcessor(logProcessor);

        logProcessorLauncher.run();
    }

    protected List<CtExecutable> getListOfExecutableFromModel(CtModel model) {

        List<CtExecutable> ctExecutables = new ArrayList<>();
        // We get each class in each model
        // Then we search each time a method is called into each method of the class
        // And we create a pair

        for (CtType<?> type : model.getAllTypes()) {

            for(CtExecutableReference ctExecutableReference : type.getDeclaredExecutables()) {
                ctExecutables.add(ctExecutableReference.getExecutableDeclaration());
            }
        }

        return ctExecutables;
    }
}
