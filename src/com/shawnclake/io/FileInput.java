package com.shawnclake.io;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileInput {

    private static String path;
    private static Scanner file;

    private FileInput(String filePath)
    {
        path = filePath;
        openFileIn();
    }

    public static boolean openFile(String filePath)
    {
        path = filePath;
        return openFileIn();
    }

    public static String readLine()
    {
        if(file.hasNextLine())
            return file.nextLine();
        return "";
    }

    public static List<String> getRemainderOfFile()
    {

        List<String> lines = new ArrayList<>();

        while (file.hasNextLine()) {
            String line = file.nextLine();
            lines.add(line);
        }

        return lines;
    }

    public static void forceCloseFile()
    {
        file.close();
    }

    private static boolean openFileIn()
    {
        try {

            file = new Scanner(Paths.get(path));

        }
        catch (IOException e) {

            e.printStackTrace();

            return false;

        }

        return true;
    }

}