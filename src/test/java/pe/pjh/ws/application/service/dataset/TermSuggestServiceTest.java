package pe.pjh.ws.application.service.dataset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.service.AppInitializer;
import pe.pjh.ws.application.service.TestContext;

class TermSuggestServiceTest {

    @Test
    void requestSourceName() {
        Assertions.assertDoesNotThrow(() -> TestContext
                .run((path, appService) -> {

                    AppInitializer initializer = appService.getInitializer();

                    //어플 초기화.
                    initializer.startUp();

                    //테스트용 번들 데이터 설치.
                    initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                    assert "cpuApiRdcrt".equals(appService.getTermSuggestService()
                            .requestSourceName(BundleDataSet.CMN_STN_TRM_6TH.topicNo, "CPU API 감면율", Notation.Camel));

                    assert "CpuApiRdcrt".equals(appService.getTermSuggestService()
                            .requestSourceName(BundleDataSet.CMN_STN_TRM_6TH.topicNo, "CPU API 감면율", Notation.Pascal));

                    assert "cpu_api_rdcrt".equals(appService.getTermSuggestService()
                            .requestSourceName(BundleDataSet.CMN_STN_TRM_6TH.topicNo, "CPU API 감면율", Notation.Snake));
                })
        );
    }

    @Test
    void requestDocumentName() {
        Assertions.assertDoesNotThrow(() -> TestContext
                .run((path, appService) -> {

                    AppInitializer initializer = appService.getInitializer();

                    //어플 초기화.
                    initializer.startUp();

                    //테스트용 번들 데이터 설치.
                    initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                    assert "CPU API 감면율".equals(appService.getTermSuggestService()
                            .requestDocumentName(BundleDataSet.CMN_STN_TRM_6TH.topicNo, "cpuApiRdcrt"));

                })
        );
    }
}