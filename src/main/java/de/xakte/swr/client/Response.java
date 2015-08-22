package de.xakte.swr.client;

import java.io.InputStream;

/**
 * Created by Ernst Bunders on 12-8-15.
 */
public class Response {
    private InputStream body;

    public Response(InputStream body) {
        this.body = body;
    }

    public InputStream getBody() {
        return body;
    }
}
