package com.shawnclake.components;

import com.shawnclake.morgencore.core.component.filesystem.FileRead;

import java.util.ArrayList;

public class FileBaseComponent extends UnpComponent {

    public FileBaseComponent(String path) {
        super();
        this.setContent(new ArrayList<>(new FileRead(path).getEntireFile()));
    }

}
