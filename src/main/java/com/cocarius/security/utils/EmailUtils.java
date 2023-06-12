package com.cocarius.security.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author LuongTDT
 */
@Component
public class EmailUtils {
    /**
     * Default Email regex pattern
     */
    private static final String DEFAULT_EMAIL_REGEX_PATTERN =  "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static  boolean isValid(String email,String pattern){
        return Pattern.compile(pattern).matcher(email).matches();
    }
    public static boolean isValid(String email){
        return isValid(email,DEFAULT_EMAIL_REGEX_PATTERN);
    }
}
