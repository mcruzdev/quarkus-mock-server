package io.quarkiverse.mock.server.deployment;

import io.quarkiverse.mock.server.runtime.MockStub;

public interface SourceClass {

    @MockStub(path = "/ping", responseBody = "pong", httpMethod = "GET", responseStatus = 200)
    void pingMock();
}
