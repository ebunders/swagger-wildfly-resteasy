package de.xakte.swr.client;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by Ernst Bunders on 12-8-15.
 */
public class Request implements Callable<Response> {
    private final URL url;

    public Request(URL url) {
        this.url = url;
    }

    @Override
    public Response call() {
        try {
            return new Response(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
