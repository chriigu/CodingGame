package org.example.cg.core.validator;

import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ExitCodeEnum;

public class CSVValidator {

    public void validateString(final String valueToValidate) {
        if(valueToValidate == null || valueToValidate.isBlank()) {
            throw new CodingGameException(ExitCodeEnum.INPUT_EMPTY.getExitCode(), "Input is empty");
        }

        boolean matches = valueToValidate.trim().matches("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)(,[-+]?(\\d+(\\.\\d*)?|\\.\\d+))*$");

        if(!matches) {
            throw new CodingGameException(ExitCodeEnum.FORMAT_ERROR.getExitCode(), "Input Format error");
        }
    }
}
