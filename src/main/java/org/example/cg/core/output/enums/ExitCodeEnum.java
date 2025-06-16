package org.example.cg.core.output.enums;

public enum ExitCodeEnum {

    OK(0),
    INPUT_EMPTY(1),
    READ_ERROR(2),
    WRITE_ERROR(3),
    FORMAT_ERROR(4);

    private final int exitCode;

    ExitCodeEnum(final int exitCode) {
        this.exitCode = exitCode;
    }

    public int getExitCode() {
        return exitCode;
    }
}
