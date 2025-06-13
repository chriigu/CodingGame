package org.example.cg;

import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.input.CLIArgsParser;

public class CodingGameApp {

    public void runApp(String[] args) throws CodingGameException {
        CLIArgsParser parser = new CLIArgsParser();
        parser.parse(args);
    }
}
