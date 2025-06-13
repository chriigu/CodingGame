package org.example.cg;

import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static int main(String[] args) {

        try {
            new CodingGameApp().runApp(args);
        } catch (CodingGameException e) {
            log.error("An error with errorCode [{}] occurred during runtime: {}", e.getErrorCode(), e.getMessage(), e);
            return e.getErrorCode();
        }

        return ReturnCodeEnum.OK.getErrorCode();
    }
}