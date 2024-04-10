package io.quarkiverse.mock.server.it;

import io.quarkiverse.mock.server.runtime.MockStub;

public interface MockServer {

    @MockStub(path = "/users/1", responseBody = "{\"id\": 1, \"name\": \"Matheus Cruz\"}", httpMethod = "GET", responseStatus = 200)
    void users();
}
