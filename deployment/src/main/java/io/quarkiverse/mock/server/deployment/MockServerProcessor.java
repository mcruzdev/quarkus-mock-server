package io.quarkiverse.mock.server.deployment;

import static io.quarkiverse.mock.server.runtime.MockServerRecorder.DEFAULT_STUBS_BASE_PATH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.Index;

import io.quarkiverse.mock.server.deployment.items.MockStubBuiltItem;
import io.quarkiverse.mock.server.runtime.MockServerConfig;
import io.quarkiverse.mock.server.runtime.MockServerRecorder;
import io.quarkiverse.mock.server.runtime.MockStub;
import io.quarkiverse.mock.server.runtime.MockStubItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ApplicationIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.http.deployment.RouteBuildItem;
import io.vertx.core.http.HttpMethod;

public class MockServerProcessor {

    private static final String FEATURE = "mock-server";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void process(BuildProducer<RouteBuildItem> routesProducer, MockServerRecorder recorder,
            BuildProducer<MockStubBuiltItem> mockStubsProducer, ApplicationIndexBuildItem applicationIndexBuildItem,
            MockServerConfig mockServerConfig) {

        List<MockStubItem> builtItems = generateMockStubBuildItems(applicationIndexBuildItem);

        String mockServerDefaultPath = mockServerConfig.mockPath.orElse(DEFAULT_STUBS_BASE_PATH);

        for (MockStubItem builtItem : builtItems) {
            mockStubsProducer.produce(new MockStubBuiltItem(builtItem.getPath(), builtItem.getResponseBody(),
                    HttpMethod.valueOf(builtItem.getHttpMethod()), builtItem.getResponseStatusCode()));
        }
        routesProducer.produce(
                //              We need to sanitize the mockServerDefaultPath
                RouteBuildItem.builder().routeFunction(mockServerDefaultPath.equals(DEFAULT_STUBS_BASE_PATH)
                        ? DEFAULT_STUBS_BASE_PATH
                        : mockServerDefaultPath + "*", recorder.routeConsumer(builtItems)).build());
    }

    List<MockStubItem> generateMockStubBuildItems(ApplicationIndexBuildItem indexBuildItem) {

        Index jandex = indexBuildItem.getIndex();
        Collection<AnnotationInstance> annotations = jandex.getAnnotations(MockStub.class);
        List<MockStubItem> builtItems = new ArrayList<>();
        if (!annotations.isEmpty()) {
            for (AnnotationInstance annotation : annotations) {
                AnnotationInstance annotationInstance = annotation.target().annotation(MockStub.class);
                String path = annotationInstance.value("path").asString();
                System.out.println("AnnotationInstance(path) " + path);
                String responseBody = annotationInstance.value("responseBody").asString();
                System.out.println("AnnotationInstance(responseBody) " + responseBody);
                String httpMethod = annotationInstance.value("httpMethod").asString();
                System.out.println("AnnotationInstance(httpMethod) " + HttpMethod.valueOf(httpMethod));
                int responseStatusCode = annotationInstance.value("responseStatus").asInt();
                System.out.println("AnnotationInstance(responseStatus) " + responseStatusCode);
                builtItems.add(new MockStubItem(path, responseBody, responseStatusCode, httpMethod));
            }
        }
        return builtItems;
    }

}
