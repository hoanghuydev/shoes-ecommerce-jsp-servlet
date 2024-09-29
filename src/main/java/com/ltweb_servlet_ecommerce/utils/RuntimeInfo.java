package com.ltweb_servlet_ecommerce.utils;

public class RuntimeInfo {

    /**
     * Method to get the name of the class and the line number of the caller
     */
    public static String getCallerClassNameAndLineNumber() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // Get the 3rd element in the stack trace because the 0th and 1st elements
        // are getCurrentThreadName and getCallerClassNameAndLineNumber
        StackTraceElement caller = stackTrace[2];

        // Returns the name of the class and the line number of the caller
        return caller.getClassName() + ":" + caller.getLineNumber();
    }
}
