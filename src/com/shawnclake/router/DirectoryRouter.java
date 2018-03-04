package com.shawnclake.router;

import com.shawnclake.morgencore.core.component.DynamicPrimitive;
import com.shawnclake.morgencore.core.component.filesystem.FileRead;
import com.shawnclake.morgencore.core.component.filesystem.Files;
import com.shawnclake.morgencore.core.component.property.Properties;
import fi.iki.elonen.NanoHTTPD;

import java.util.ArrayList;

public class DirectoryRouter extends Router {

    public DirectoryRouter(NanoHTTPD.IHTTPSession session) {
        super(session);
    }

    @Override
    public void route() {

        int totalComponents = this.uriComponents.size();

        int i = 0;

        Files files = new Files();

        //String path = this.pathToSrc;
        String path = this.pathToSrc;

        Properties properties = new Properties();

        while(i < totalComponents)
        {
            path += '/' +  this.uriComponents.get(i);

            System.out.println(path);

            ArrayList<String> contents = files.listFileNames(path);

            // If the current directory is empty, the route is not found
            if(files.isDirectory(path) && contents.size() < 1)
            {
                this.file = new FileRead(this.pathToSrc + "/unp/404.unp");
                return;
            }

            System.out.println(contents);

            // TODO: logic for var.unp, index.unp, help.unp, params.unp goes here

            if(contents.contains("params.unp"))
            {
                System.out.println("params found");
                this.parameterHandlers.add(path + "/params.unp");
            }


            // Handles vars as we need to
            if(contents.contains("var.unp"))
            {
                System.out.println("var found");

                i++;

                // If the directory requires a var but no var is in the uri
                if(i == totalComponents)
                {
                    System.out.println("var value was not found in the route");
                    this.file = new FileRead(this.pathToSrc + "/unp/404.unp");
                    return;
                }

                System.out.println(this.uriComponents.get(i));

                properties.add("prop"+i, new DynamicPrimitive(this.uriComponents.get(i)));
            }

            if(i == totalComponents - 1 && "help".equals(this.uriComponents.get(i)))
            {

                if(!contents.contains("help.unp"))
                {
                    boolean foundHelp = false;

                    for(int j = i; j > 0; j--)
                    {
                        System.out.println("need some help: " + contents.toString());
                        path = path.substring(0, path.lastIndexOf('/'));
                        System.out.println("new path: " + path);
                        contents = files.listFileNames(path);
                        if(contents.contains("help.unp"))
                        {
                            System.out.println("help found here: " + path);
                            path += "/help";
                            foundHelp = true;
                            break;
                        }
                    }

                    // Cant find any help files
                    if(!foundHelp)
                    {
                        System.out.println("Cant find any HELP");
                        this.file = new FileRead(this.pathToSrc + "/unp/404.unp");
                        return;
                    }

                }

            }

            i++;
        }

        for(DynamicPrimitive prop : properties.getProperties().values())
        {
            System.out.println(prop.getInt());
        }

        if(files.isDirectory(path))
        {
            ArrayList<String> contents = files.listFileNames(path);
            if(contents.contains("index.unp"))
            {
                path += "/index";
            }
        }

        path += ".unp";

        if(!files.isFile(path))
        {
            this.file = new FileRead(this.pathToSrc + "/unp/404.unp");
            return;
        }

        System.out.println("Final Path: " + path);

        this.file = new FileRead(path);

    }
}
