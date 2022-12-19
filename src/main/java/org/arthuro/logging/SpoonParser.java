package org.arthuro.logging;

import spoon.Launcher;

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

        logProcessorLauncher.addInputResource("./src/main/java/org/arthuro/cli");
        logProcessorLauncher.setSourceOutputDirectory("./spooned/src/main/java");
        logProcessorLauncher.addProcessor(logProcessor);

        logProcessorLauncher.run();
    }

}
