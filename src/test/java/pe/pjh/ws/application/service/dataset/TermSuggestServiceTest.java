package pe.pjh.ws.application.service.dataset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.service.AppInitializer;
import pe.pjh.ws.application.service.TestContext;

class TermSuggestServiceTest {

    @Test
    void requestSourceName() {
        Assertions.assertDoesNotThrow(() -> {
            TestContext.run((path, appService) -> {

                AppInitializer initializer = appService.getInitializer();

                //어플 초기화.
                initializer.startUp();

                //테스트용 번들 데이터 설치.
                initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                String wordCount = appService.getTermSuggestService()
                        .requestSourceName(BundleDataSet.CMN_STN_TRM_6TH.topicNo, "CPU API 감면율");
                System.out.println(wordCount);
            });
        });
    }

    @Test
    void requestDocumentName() {
    }
}