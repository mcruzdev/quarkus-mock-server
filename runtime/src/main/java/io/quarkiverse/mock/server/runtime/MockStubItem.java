package io.quarkiverse.mock.server.runtime;

public class MockStubItem {
    private String path;
    private String responseBody;
    private int responseStatusCode;
    private String httpMethod;

    public MockStubItem() {
    }

    public MockStubItem(String path, String responseBody, int responseStatusCode, String httpMethod) {
        this.path = path;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
