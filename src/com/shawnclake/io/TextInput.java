package com.shawnclake.io;
import java.util.Scanner;

public final class TextInput {

    private static Scanner scanner;

    private TextInput()
    {
        scanner = new Scanner(System.in);
    }

    public static Scanner getScanner()
    {
        return scanner;
    }

    public static String readLine()
    {
        if(scanner.hasNextLine())
            return scanner.nextLine();

        return "";
    }


}
