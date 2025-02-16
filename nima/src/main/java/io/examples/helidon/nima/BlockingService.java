package io.examples.helidon.nima;

import io.exampel.helidon.microstream.Storage;
import io.helidon.nima.webserver.http.HttpRules;
import io.helidon.nima.webserver.http.HttpService;
import io.helidon.nima.webserver.http.ServerRequest;
import io.helidon.nima.webserver.http.ServerResponse;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class BlockingService implements HttpService {

    private static final Storage storage = new Storage();

    @Override
    public void routing(HttpRules httpRules) {
        httpRules.get("/one", this::one)
                .get("/sequence", this::sequence)
                .get("/parallel", this::parallel)
                .get("/sleep", this::sleep);
    }

    private void sleep(ServerRequest req, ServerResponse res) throws Exception {
        Thread.sleep(1000);
        res.send("finished");
    }

    private void one(ServerRequest req, ServerResponse res) {
        String response = storage.getRandomValue();

        res.send(response);
    }

    private void sequence(ServerRequest req, ServerResponse res) {
        int count = count(req);

        var responses = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            responses.add(storage.getRandomValue());
        }

        res.send("Combined results: " + responses);
    }

    private void parallel(ServerRequest req, ServerResponse res) throws Exception {

        try (var exec = Executors.newVirtualThreadPerTaskExecutor()) {
            int count = count(req);

            var futures = new ArrayList<Future<String>>();
            for (int i = 0; i < count; i++) {
                futures.add(exec.submit(storage::getRandomValue));
            }

            var responses = new ArrayList<String>();
            for (var future : futures) {
                responses.add(future.get());
            }

            res.send("Combined results: " + responses);
        }
    }

    private int count(ServerRequest req) {
        return req.query().first("count").map(Integer::parseInt).orElse(3);
    }
}
