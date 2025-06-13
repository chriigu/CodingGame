package org.example.cg;

import org.example.cg.core.exception.CodingGameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            log.info("Starting coding game with arguments {}", Arrays.toString(args));
            System.exit(new CodingGameApp().runApp(args));
        } catch (CodingGameException e) {
            log.error("An error with errorCode [{}] occurred during runtime: {}", e.getErrorCode(), e.getMessage(), e);
            System.exit(e.getErrorCode());
        }
    }
}