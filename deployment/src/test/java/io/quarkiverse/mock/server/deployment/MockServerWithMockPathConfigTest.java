package io.quarkiverse.mock.server.deployment;

import org.hamcrest.Matchers;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;

class MockServerWithMockPathConfigTest {

    @RegisterExtension
    final static QuarkusUnitTest dev = new QuarkusUnitTest().withApplicationRoot((jar) -> jar.addClass(SourceClass.class)
            .add(new StringAsset("quarkus.mock-server.mock-path=/devconverge/"), "application.properties"));

    @Test
    public void shouldReturns200() {
        RestAssured.get("/devconverge/ping").then().statusCode(200).body(Matchers.is("pong"));
    }

    @Test
    public void shouldReturn404() {
        RestAssured.get("/devconverge/hi").then().statusCode(404).body(Matchers.containsString("There is no"));
    }
}
