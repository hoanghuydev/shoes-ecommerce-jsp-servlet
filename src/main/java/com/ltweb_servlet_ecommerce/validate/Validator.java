package com.ltweb_servlet_ecommerce.validate;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;

public class Validator {

    // Validate email
    public static boolean isValidEmail(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }


    // Validate using custom regex
    public static boolean isValidWithRegex(String value, String regex) {
        RegexValidator regexValidator = new RegexValidator(regex);
        return regexValidator.isValid(value);
    }

    // Validate if string is not null and not empty
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validate if string length is within a specified range
    public static boolean isLengthValid(String value, int minLength, int maxLength) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }
    public static boolean isMaxLengthValid(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return isValidWithRegex(phoneNumber, "^0\\d{9}$");
    }

    public static void main(String[] args) {
        System.out.println(isValidEmail("a.gmail.com"));
        System.out.println(isValidEmail("abc@gmail.com"));
    }
}
