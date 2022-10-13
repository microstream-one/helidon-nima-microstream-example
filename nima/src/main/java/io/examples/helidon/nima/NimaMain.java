package io.examples.helidon.nima;

import io.helidon.common.http.Http;
import io.helidon.nima.webserver.WebServer;
import io.helidon.nima.webserver.http.HttpRouting;

public class NimaMain {
    private static final Http.HeaderValue SERVER = Http.Header.create(Http.Header.SERVER, "Nima");

    public static void main(String[] args) {
        WebServer.builder()
                .routing(NimaMain::routing)
                .start();
    }

    static void routing(HttpRouting.Builder rules) {
        rules.addFilter((chain, req, res) -> {
                    res.header(SERVER);
                    chain.proceed();
                })
                .register("/", new BlockingService());
    }

}
