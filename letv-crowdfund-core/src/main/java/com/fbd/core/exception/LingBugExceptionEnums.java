package com.fbd.core.exception;

public enum LingBugExceptionEnums implements ExceptionEnums {
    
    NO_LOGIN(10000,"用户未登录");
    
    public int code;  
    public String message;  
      
    private LingBugExceptionEnums(int code, String message){  
        this.code = code;  
        this.message = message;  
    } 
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }

}
