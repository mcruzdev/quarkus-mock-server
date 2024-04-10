package io.quarkiverse.mock.server.deployment.items;

import io.quarkus.builder.item.MultiBuildItem;
import io.vertx.core.http.HttpMethod;

public final class MockStubBuiltItem extends MultiBuildItem {

    private final String path;
    private final String responseBody;
    private final HttpMethod httpMethod;
    private final int responseStatusCode;

    public MockStubBuiltItem(String path, String responseBody, HttpMethod httpMethod, int responseStatusCode) {
        this.path = path;
        this.responseBody = responseBody;
        this.httpMethod = httpMethod;
        this.responseStatusCode = responseStatusCode;
    }

    public String getPath() {
        return path;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public String getHttpMethodName() {
        return httpMethod.name();
    }
}
