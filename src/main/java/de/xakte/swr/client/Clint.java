package de.xakte.swr.client;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ernst Bunders on 12-8-15.
 * http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 */
public class Clint {

    public static void main(String[] args) throws MalformedURLException, ExecutionException, InterruptedException {
        final Clint clint = new Clint();
        int jobs = 5;
        CompletableFuture.allOf(clint.loop(jobs, 200, (i) -> () -> clint.communicate(i)).toArray(new CompletableFuture[jobs]))
                .thenRun(() -> System.exit(0));
    }

//    public void doit() throws ExecutionException, InterruptedException {
//        print("get started");
//        loop(10, 10, this::startjob);
//
//        print("done asking, moving on...");
//        sleep(1000 * 60, "shutdown", () -> "now");
//    }

    public List<CompletableFuture> loop(int times, int threads, Function<Integer, Runnable> jobFactory) {
        List<CompletableFuture> l = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            l.add(startjob(Executors.newFixedThreadPool(threads), jobFactory.apply(i)));
        }
        return l;
    }

    public CompletableFuture startjob(ExecutorService es, Runnable job) {
        return CompletableFuture.runAsync(job, es);

    }

    public void ask(Consumer<String> consumer, int i) {
        CompletableFuture.supplyAsync(() -> sleep(2000, "stage1", () -> "hallo"))
                .thenApply(s -> sleep(2000, "stage2", () -> s + " Ernst"))
                .thenApply(s -> maybe(s, i))
                .exceptionally(ex -> "We have a problem:" + ex.getMessage())
                .thenAccept(consumer)
        ;

    }

    public void communicate(int i) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            HttpGet httpget = new HttpGet("http://localhost:8080/swr/resources/async/t1");
            httpget.setHeader("Accept", "Application/Json");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            print("response nr " + i + ":" + responseBody);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(String s) {
        System.out.println(s);
    }


    public <T> T sleep(int millis, String id, Supplier<T> supplier) {
        try {
            Thread.sleep(millis);
        } catch (Throwable t) {
        }
        if (id.length() > 0) print(id);
        return supplier.get();
    }

    public void sleep(int millis, String id, Runnable r) {
        try {
            Thread.sleep(millis);
        } catch (Throwable t) {
        }
        if (id.length() > 0) print(id);
        r.run();
    }

    public String maybe(String s, int i) {
        if (i == 1) {
            throw new RuntimeException("poof!");
        }
        return s;
    }
}
