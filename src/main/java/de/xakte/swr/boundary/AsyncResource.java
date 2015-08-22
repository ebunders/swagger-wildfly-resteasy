package de.xakte.swr.boundary;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static de.xakte.swr.boundary.Util.json;

/**
 * Created by Ernst Bunders on 13-8-15.
 */
@Path("/async")
@Api(value = "/async", description = "Some async experiments")

@Stateless
public class AsyncResource {
    private ExecutorService es = Executors.newSingleThreadExecutor();

    @GET
    @Path("/t1")
    @ApiOperation(value = "message", notes = "get a message after 5 seconds ")
    public void getMessage(@Suspended final AsyncResponse asyncResponse) {
        CompletableFuture.runAsync(() -> {
            print("Starting sleep");
            eatEx(() -> Thread.sleep(5000));
            print("Resuming response handling");
            asyncResponse.resume(Response.ok(json("message", "Message after 5 seconds")).build());
        }, es);
        print("Request handler done");
    }

    @GET
    @Path("/t2")
//    @Asynchronous
    @ApiOperation(value = "message", notes = "get a message after five seconds, but whithout explicitely using a CompletableFuture.")
    public void getT2(@Suspended final AsyncResponse asyncResource) {
        print("Starting sleep");
        eatEx(() -> Thread.sleep(5000));
        print("Resuming response handling");
        asyncResource.resume(Response.ok(json("message", "Message after 5 seconds")).build());
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void eatEx(ExceptionalRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
        }
    }

    @FunctionalInterface
    public interface ExceptionalRunnable {
        public void run() throws Throwable;
    }
}
