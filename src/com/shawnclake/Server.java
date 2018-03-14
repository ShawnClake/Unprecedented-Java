package com.shawnclake;

import com.shawnclake.components.FileBaseComponent;
import com.shawnclake.components.LoginComponent;
import com.shawnclake.components.TestComponent;
import com.shawnclake.morgencore.core.component.html.HtmlBuilder;
import com.shawnclake.morgencore.core.component.html.HtmlPageBuilder;
import com.shawnclake.morgencore.core.component.html.attributes.Class;
import com.shawnclake.morgencore.core.component.html.attributes.Style;
import com.shawnclake.morgencore.core.component.html.components.*;
import com.shawnclake.morgencore.core.component.html.libraries.ExternalHtmlLibrary;
import com.shawnclake.morgencore.core.component.html.libraries.HtmlLibraryType;
import com.shawnclake.morgencore.core.component.html.parsing.DynamicHtml;
import com.shawnclake.morgencore.core.component.html.tags.TagBuilder;
import com.shawnclake.router.DirectoryRouter;
import com.shawnclake.router.Router;
import fi.iki.elonen.NanoHTTPD;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Server extends NanoHTTPD {

    public Server(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://<host>:" + port + "/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {

        Router router = new DirectoryRouter(session);
        router.route();

        //String msg = "<html><body>\n";

        HtmlPageBuilder htmlPageBuilder = HtmlPageBuilder.build("Fixed Response");
        htmlPageBuilder.addLibrary(new ExternalHtmlLibrary("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"), true);
        htmlPageBuilder.addLibrary(new ExternalHtmlLibrary("https://code.jquery.com/jquery-3.2.1.slim.min.js", HtmlLibraryType.SCRIPT));
        htmlPageBuilder.addLibrary(new ExternalHtmlLibrary("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js", HtmlLibraryType.SCRIPT));
        htmlPageBuilder.addLibrary(new ExternalHtmlLibrary("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js", HtmlLibraryType.SCRIPT));

        htmlPageBuilder.appendBody(new HtmlBuilderComponent(HtmlBuilder.build(new TextComponent("what up")).append(new TextComponent("big cheeseburger butts"))));

        htmlPageBuilder.appendBody(new HtmlBuilderComponent(HtmlBuilder.build(new TagComponent(TagBuilder.build(HTML.Tag.STRONG)
                .addAttribute(new Style("font-size", "45px").add("color","red"))
                .getFullTag(new TextComponent("I kinda hope this works"))))));

        htmlPageBuilder.appendBody(new LineBreakComponent());

        htmlPageBuilder.appendBody(new LoremIpsumComponent());

        htmlPageBuilder.appendBody(new LineBreakComponent());
        htmlPageBuilder.appendBody(new LineBreakComponent());

        GroupComponent container = new GroupComponent(new TagComponent(TagBuilder.build(HTML.Tag.H4).getFullTag(new TextComponent("Regular Login"))));
        container.append(new LoginComponent());

        container.append(new LineBreakComponent());
        container.append(new LineBreakComponent());

        container.append(new TagComponent(TagBuilder.build(HTML.Tag.H4).getFullTag(new TextComponent("Fancy Login"))));
        container.append(new FileBaseComponent("lib/components/fancy-login.unp"));

        container.append(new LineBreakComponent());
        container.append(new TestComponent());

        htmlPageBuilder.appendBody(new TagComponent(new TagBuilder(HTML.Tag.DIV).addAttribute(new Class("container")).getFullTag(container)));

        DynamicHtml dynamicHtml = new DynamicHtml("<p>I am some bullshit {{ shit }}, but that's okay I don't care that {{ much }} :)");
        dynamicHtml.compileVariables(new HashMap<>(Map.of("shit","human guy", "much", "guy is a dumby")));
        htmlPageBuilder.appendBody(new HtmlComponent(dynamicHtml));

        //htmlBuilder.append(new StyleImportComponent(new ExternalHtmlLibrary("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css")));
        //htmlBuilder.append(new ScriptImportComponent(new ExternalHtmlLibrary("https://code.jquery.com/jquery-3.2.1.slim.min.js")));
        //htmlBuilder.append(new ScriptImportComponent(new ExternalHtmlLibrary("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js")));
        //htmlBuilder.append(new ScriptImportComponent(new ExternalHtmlLibrary("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js")));

        //msg += htmlBuilder.getHtml().getHtmlString();

        for(String part : router.getFile().getEntireFile())
        {
            htmlPageBuilder.appendBody(new TextComponent(part));
        }


        /*Map<String, String> parms = session.getParms();

        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }*/
        return newFixedLengthResponse(htmlPageBuilder.getHtml().getHtmlString());
    }

}
