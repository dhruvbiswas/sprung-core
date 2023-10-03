package com.sprung.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp {

    public static boolean isMatch(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if(m.find()) {
            return true;
        } else {
            return false;
        }
    }
    
}
