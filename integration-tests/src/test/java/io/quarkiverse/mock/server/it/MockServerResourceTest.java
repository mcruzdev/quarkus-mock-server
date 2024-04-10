package io.quarkiverse.mock.server.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MockServerResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/mock-server")
                .then()
                .statusCode(200)
                .body(is("Hello mock-server"));
    }
}
