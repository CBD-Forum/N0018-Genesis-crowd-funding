package com.fbd.core.exception;

public class LingBugException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3931582346716156126L;
    
    private ExceptionEnums exceptionEnums;

    public LingBugException(ExceptionEnums exceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }

    public ExceptionEnums getExceptionEnums() {
        return exceptionEnums;
    }
    
}
