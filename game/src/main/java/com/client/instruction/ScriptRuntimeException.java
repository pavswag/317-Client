package com.client.instruction;

import lombok.Getter;

public class ScriptRuntimeException extends Exception {
    @Getter
    private final String exception;
    public ScriptRuntimeException(String exception) {
        super(exception);
        this.exception = exception;
    }
}
