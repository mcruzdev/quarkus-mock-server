package io.quarkiverse.mock.server.deployment.devui;

import io.quarkiverse.mock.server.deployment.items.MockStubBuiltItem;
import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devui.spi.page.CardPageBuildItem;
import io.quarkus.devui.spi.page.Page;

import java.util.List;

public class MockServerDevUIProcessor {

   @BuildStep(onlyIf = IsDevelopment.class)
   public CardPageBuildItem pages(List<MockStubBuiltItem> mockStubs) {
      CardPageBuildItem cardPageBuildItem = new CardPageBuildItem();

      cardPageBuildItem.addBuildTimeData("mockStubs", mockStubs);

      int mockStubsSize = mockStubs.size();

      cardPageBuildItem.addPage(Page.externalPageBuilder("Github")
            .url("https://github.com/mcruzdev/quarkus-mock-server",
                  "https://github.com/mcruzdev/quarkus-mock-server").icon("font-awesome-solid:file-lines"));

      cardPageBuildItem.addPage(Page.tableDataPageBuilder("Mock stubs").showColumn("path").showColumn("responseBody")
            .showColumn("httpMethodName").showColumn("responseStatusCode").buildTimeDataKey("mockStubs")
            .icon("font-awesome-solid:file-code").staticLabel(String.valueOf(mockStubsSize)));

      return cardPageBuildItem;
   }
}
