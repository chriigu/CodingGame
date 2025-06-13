package org.example.cg.core.exception;

public class CodingGameException extends RuntimeException{

    private final int errorCode;

    public CodingGameException(final int errorCode){
        super();
        this.errorCode = errorCode;
    }

    public CodingGameException(final int errorCode, final String message){
        super(message);
        this.errorCode = errorCode;
    }

    public CodingGameException(final int errorCode, final String message, final Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
