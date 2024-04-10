package io.quarkiverse.mock.server.runtime;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MockStub {
    String path();

    int responseStatus();

    String responseBody();

    String httpMethod();
}
