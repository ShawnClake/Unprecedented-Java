package com.shawnclake;

import com.shawnclake.compiler.Directorizer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        /*FileInput.openFile("C:\\TestFiles\\file.txt");
        List<String> lines = FileInput.getRemainderOfFile();

        for(String temp : lines)
        {
            System.out.println(temp);
        }*/


        Directorizer directorizer = new Directorizer();
        directorizer.compile();


        try {
            Server server = new Server(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
