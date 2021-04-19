package com.adeemm.expiry;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class exists to convert a string into the format we need it
 */
public class Utils {

    // https://stackoverflow.com/questions/12387492/how-do-i-convert-a-string-to-title-case-in-android

    /**
     * Pre:s is a string
     * Post: returns a string in Title Case
     */
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

    /**
     * This string turns the time into a formatted date string for our item entry form
     */
    public static String getFormattedDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        return format.format(new Date(time));
    }
}
