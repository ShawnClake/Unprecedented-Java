package com.shawnclake;

import com.shawnclake.io.FileInput;
import com.shawnclake.router.Server;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*FileInput.openFile("C:\\TestFiles\\file.txt");
        List<String> lines = FileInput.getRemainderOfFile();

        for(String temp : lines)
        {
            System.out.println(temp);
        }*/

        try {
            Server server = new Server(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
