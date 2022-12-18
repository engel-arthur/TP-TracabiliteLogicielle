package org.arthuro.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class CLIWidget {
    Map<String, String> prompts;
    Scanner userInputScanner;

    public CLIWidget(Scanner userInputScanner) {
        this.userInputScanner = userInputScanner;
    }

    public CLIWidget(){}

    public abstract void execute() throws Exception;
}
