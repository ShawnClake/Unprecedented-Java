package com.shawnclake.router;

import com.shawnclake.morgencore.core.component.Collections;
import com.shawnclake.morgencore.core.component.filesystem.FileRead;
import fi.iki.elonen.NanoHTTPD;

import java.util.ArrayList;

abstract public class Router {

    protected String pathToSrc = "/home/shawn/src";

    private NanoHTTPD.IHTTPSession session;

    protected ArrayList<String> uriComponents = new ArrayList<>();

    protected ArrayList<String> uriIgnores = new ArrayList<>();

    protected FileRead file;

    public Router(NanoHTTPD.IHTTPSession session) {

        this.uriIgnores.add("favicon.ico"); // Stop this POST request from going through this system
        this.uriIgnores.add(".."); // Stop people from trying to backtrace directories
        this.uriIgnores.add(".");

        this.session = session;

        String uri = session.getUri().substring(1); // Removes the initial '/' from the URI

        if(this.uriIgnores.contains(uri))  // Check for things like favicon before bothering to move forward
            return;

        uriComponents.addAll(Collections.toArrayList(uri.split("/"))); // Split the URI into components now

        for(String comp : uriComponents)
        {
            if(this.uriIgnores.contains(comp))  // If any URI component has the forbidden characters, stop everything
                return;
        }

        //System.out.println(uriComponents);
    }

    public FileRead getFile() {
        return file;
    }

    abstract public void route();


}
