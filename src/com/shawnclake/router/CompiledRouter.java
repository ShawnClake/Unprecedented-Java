package com.shawnclake.router;

import fi.iki.elonen.NanoHTTPD;

public class CompiledRouter extends Router {

    public CompiledRouter(NanoHTTPD.IHTTPSession session) {
        super(session);
    }

    @Override
    public void route() {

    }
}
