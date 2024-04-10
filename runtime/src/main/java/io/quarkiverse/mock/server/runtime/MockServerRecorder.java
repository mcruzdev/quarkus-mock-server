package io.quarkiverse.mock.server.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import io.quarkus.runtime.annotations.Recorder;
import io.vertx.core.Handler;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

@Recorder
public class MockServerRecorder {
    public static final String DEFAULT_STUBS_BASE_PATH = "/stubs/*";
    private static final String DEFAULT_STUBS_ROUTE = "/stubs";

    private final MockServerConfig mockServerConfig;

    public MockServerRecorder(MockServerConfig mockServerConfig) {
        this.mockServerConfig = mockServerConfig;
    }

    public Consumer<Route> routeConsumer(List<MockStubItem> mockStubItems) {

        return new Consumer<Route>() {
            @Override
            public void accept(Route route) {

                HashMap<String, MockStubItem> stubs = new HashMap<>();

                for (MockStubItem mockStubItem : mockStubItems) {

                    String path = mockStubItem.getPath();
                    while (path.startsWith("/")) {
                        path = path.substring(1);
                    }

                    stubs.put(mockServerConfig.mockPath.orElse(DEFAULT_STUBS_ROUTE) + path, mockStubItem);
                }

                route.handler(new Handler<RoutingContext>() {
                    @Override
                    public void handle(RoutingContext ctx) {

                        MockStubItem mockStubItem = stubs.get(ctx.request().path());
                        if (mockStubItem != null) {
                            ctx.response().setStatusCode(mockStubItem.getResponseStatusCode())
                                    .send(mockStubItem.getResponseBody());
                        } else {
                            ctx.response().setStatusCode(404).send("{\"message\": \"There is no MockStub :(\"}");
                        }
                    }
                });

            }
        };
    }
}
