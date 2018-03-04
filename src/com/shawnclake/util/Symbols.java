package com.shawnclake.util;


public final class Symbols {

    private static String enclosers = "()[]{}<>";

    private static String math = "+-/*^";

    private static String quotes = "\"'`'";

    private static String concatenators = "+&";

    private static String puncutation = ".?!,";

    public static String getEnclosers() {
        return enclosers;
    }

    public static String getMath() {
        return math;
    }

    public static String getQuotes() {
        return quotes;
    }

    public static String getConcatenators() {
        return concatenators;
    }

    public static String getPuncutation() {
        return puncutation;
    }

    public static boolean isRightParenthesis(char c)
    {
        return c == ')';
    }

    public static boolean isLeftParenthesis(char c)
    {
        return c == '(';
    }

    public static boolean isRightCurlyBrace(char c)
    {
        return c == '}';
    }

    public static boolean isLeftCurlybrace(char c)
    {
        return c == '{';
    }

    public static boolean isRightSquareBracket(char c)
    {
        return c == ']';
    }

    public static boolean isLeftSquareBracket(char c)
    {
        return c == '[';
    }

    public static boolean isNewLineChar(char c)
    {
        return (c == '\n') || (c == '\r');
    }

    public static boolean isSpaceChar(char c)
    {
        return (c == ' ');
    }

}
