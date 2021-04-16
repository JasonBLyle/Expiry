package com.adeemm.expiry;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    // https://stackoverflow.com/questions/12387492/how-do-i-convert-a-string-to-title-case-in-android
    public static String convertToTitleCase(String s) {
        boolean whiteSpace = true;

        StringBuilder builder = new StringBuilder(s);
        final int builderLength = builder.length();
        
        for (int i = 0; i < builderLength; ++i) {
            char c = builder.charAt(i);

            if (whiteSpace) {
                if (!Character.isWhitespace(c)) {
                    builder.setCharAt(i, Character.toTitleCase(c));
                    whiteSpace = false;
                }
            } 
            else if (Character.isWhitespace(c)) {
                whiteSpace = true;

            } 
            else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String getFormattedDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        return format.format(new Date(time));
    }
}
