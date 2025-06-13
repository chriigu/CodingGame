package org.example.cg.core.output.enums;

public enum ReturnCodeEnum {

    OK(0),
    INPUT_EMPTY(1),
    READ_ERROR(2),
    WRITE_ERROR(3),
    FORMAT_ERROR(4);

    private final int errorCode;

    ReturnCodeEnum(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
