package com.shawnclake.components;

import com.shawnclake.morgencore.core.component.html.components.HtmlComponent;
import com.shawnclake.morgencore.core.component.html.parsing.Html;

import java.util.ArrayList;

public class UnpComponent extends HtmlComponent {

    public UnpComponent() {
        super(new Html(""));
    }

    public void setContent(ArrayList<String> ComponentContents)
    {
        for(String line : ComponentContents)
            this.getHtml().appendHtml(line);
    }

}
