package com.shawnclake.compiler;

import com.shawnclake.morgencore.core.component.Strings;
import com.shawnclake.morgencore.core.component.filesystem.FileRead;
import com.shawnclake.morgencore.core.component.filesystem.Files;
import com.shawnclake.morgencore.core.component.messages.ListMessage;
import com.shawnclake.morgencore.core.component.messages.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Directorizer {

    private String pathToSrc = "/home/shawn/src";
    private String pathToCompiled = "/home/shawn/compiled";
    private Files files = new Files();

    private DirectorizerStatistics directorizerStatistics = new DirectorizerStatistics();

    private void maker(File file)
    {
        //if("compiled".equals(file.getName()))
        //    return;

        if(file == null)
            return;

        for(File f : file.listFiles())
        {
            if(f.isDirectory())
                this.maker(f);

            ArrayList<String> contents = files.listFileNames(file);

            if(files.getFileExtension(f, true).equals(".unp"))
            {
                // TODO: remove the src path from this path
                // swap '/' for '.'
                // prefix 'route.'
                // append '.cunp'
                // copy source of the file over
                // place into src folder/compiled

                String path = f.getAbsolutePath().substring(pathToSrc.length() + 1, f.getAbsolutePath().length() - 4);

                int dirCount = Strings.countOccurrences(path, '/');

                if(path.contains("/") && "params".equals(path.substring(path.lastIndexOf('/') + 1)))
                    continue;

                if(path.contains("/") && ("var".equals(path.substring(path.lastIndexOf('/') + 1)) || "help".equals(path.substring(path.lastIndexOf('/') + 1)) ))
                {
                    dirCount--;
                }

                int lastIndex = 0, offset = 0;

                String newPath = path;

                for(int i = 0; i < dirCount; i++)
                {
                    lastIndex = path.indexOf('/', lastIndex);
                    String subPath =  pathToSrc + "/" + path.substring(0, lastIndex) + "/" + "var.unp";
                    //System.out.println("Subpath is: " + i + ": " + subPath);
                    if(files.isExist(subPath))
                    {
                        //String newpath = path.substring(0, lastIndex) + "/var/" + path.substring(lastIndex + 1);

                        newPath = newPath.substring(0, lastIndex + offset * 4) + "/var" + newPath.substring(lastIndex + offset * 4);

                        //System.out.println("Subpath Exists: " + i + ": " + subPath);
                        //System.out.println("Newpath beecomes: " + i + ": " + newPath);
                        offset++;
                        //lastIndex += 3;

                    }

                    lastIndex++;
                }

                newPath = newPath.replace('/', '.');
                newPath = "route." + newPath;
                newPath += ".cunp";
                newPath = pathToCompiled + "/" + newPath;

                Message fileContents = null;

                try {
                    System.out.println(f.getAbsolutePath());
                    FileRead fileRead = new FileRead(f.getAbsolutePath());
                    fileContents = new ListMessage(new ArrayList<>(fileRead.getEntireFile()));
                    System.out.println(fileContents.toString());
                    fileRead.forceCloseFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(fileContents != null)
                {
                    files.createFile(newPath, (ListMessage) fileContents);

                    //System.out.println("Path of compilation: " + newPath);
                    this.directorizerStatistics.addNewRoute(newPath);
                } else {
                    System.out.println("Failed to compile " + f.getAbsolutePath());
                }

            }
        }
    }

    public void compile()
    {
        if(!files.isExist(pathToCompiled))
        {
            files.createDirectory(pathToCompiled);
        }

        this.maker(new File(pathToSrc));

        System.out.println("Generated " + this.directorizerStatistics.getTotalRoutes() + " routes:");
        for(int i = 0; i < this.directorizerStatistics.getTotalRoutes(); i++)
        {
            System.out.println(this.directorizerStatistics.getRoute(i));
        }


    }

}
