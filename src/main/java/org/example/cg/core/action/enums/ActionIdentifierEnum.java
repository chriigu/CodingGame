package org.example.cg.core.action.enums;

import org.example.cg.core.exception.CodingGameException;
import org.example.cg.core.output.enums.ExitCodeEnum;

public enum ActionIdentifierEnum {

    SUM("sum"),
    MIN_MAX("minmax"),
    LT4("lt4");

    private final String actionIdentifier;

    ActionIdentifierEnum(final String actionIdentifier) {
        this.actionIdentifier = actionIdentifier;
    }

    public String getActionIdentifier() {
        return actionIdentifier;
    }

    public static ActionIdentifierEnum getEnum(final String actionIdentifier) {
        if (actionIdentifier == null) {
            throw new CodingGameException(ExitCodeEnum.FORMAT_ERROR.getExitCode(), "No such actionIdentifier exists: null");
        }

        ActionIdentifierEnum[] enums = ActionIdentifierEnum.values();
        for (ActionIdentifierEnum value : enums) {
            if (value.getActionIdentifier().equals(actionIdentifier)) {
                return value;
            }
        }
        throw new CodingGameException(ExitCodeEnum.FORMAT_ERROR.getExitCode(), "No such actionIdentifier exists: " + actionIdentifier);
    }
}
