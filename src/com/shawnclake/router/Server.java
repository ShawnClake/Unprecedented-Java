package com.shawnclake.router;

import fi.iki.elonen.NanoHTTPD;

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

        String msg = "<html><body><h1>Hello server</h1>\n";

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
