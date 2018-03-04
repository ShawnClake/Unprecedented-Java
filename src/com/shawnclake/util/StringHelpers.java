package com.shawnclake.util;

public final class StringHelpers {

    public static String removeLineBreaks(String string)
    {
        String[] lines = string.split("\r\n|\r|\n");

        String oneLiner = "";

        for (String line : lines) {
            oneLiner += line;
        }

        return oneLiner;
    }

}
