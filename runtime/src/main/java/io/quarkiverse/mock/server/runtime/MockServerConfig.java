package io.quarkiverse.mock.server.runtime;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class MockServerConfig {

    /**
     * The base path for mock server
     */
    public Optional<String> mockPath;
}
