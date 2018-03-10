package com.shawnclake;

import com.shawnclake.morgencore.core.component.html.HtmlBuilder;
import com.shawnclake.morgencore.core.component.html.attributes.Attribute;
import com.shawnclake.morgencore.core.component.html.attributes.Style;
import com.shawnclake.morgencore.core.component.html.components.TagComponent;
import com.shawnclake.morgencore.core.component.html.components.TextComponent;
import com.shawnclake.morgencore.core.component.html.tags.RenderedTag;
import com.shawnclake.morgencore.core.component.html.tags.TagBuilder;
import com.shawnclake.router.DirectoryRouter;
import com.shawnclake.router.Router;
import fi.iki.elonen.NanoHTTPD;

import javax.swing.text.html.HTML;
import java.io.IOException;
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

        String msg = "<html><body>\n";

        HtmlBuilder htmlBuilder = new HtmlBuilder(new TextComponent("what up")).append(new TextComponent("big cheeseburger butts"));

        htmlBuilder.append(new TagComponent(TagBuilder.build(HTML.Tag.STRONG).addAttribute(new Style().add("font-size", "45px").add("color","red")).getTag().getFullTag(new TextComponent("I kinda hope this works"))));

        msg += htmlBuilder.getHtml().getHtmlString();


        try {
            for(String part : router.getFile().getEntireFile())
            {
                msg += part;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Map<String, String> parms = session.getParms();

        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }*/
        return newFixedLengthResponse(msg + "</body></html>\n");
    }



}
