package pe.pjh.ws.application.service;

import com.intellij.openapi.diagnostic.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.service.dataset.BundleDataSet;

@DisplayName("앱 초기화 테스트")
class AppInitializerTest {

    private static final Logger LOG = Logger.getInstance(AppInitializerTest.class);

    /**
     * 앱 최초 실행 및 번들 데이터 설치 테스트
     */
    @Test
    @DisplayName("앱 최초 실행 테스트")
    public void initializeTest() {

        Assertions.assertDoesNotThrow(() -> {
            TestContext.run((path, appService) -> {

                AppInitializer initializer = appService.getInitializer();

                //어플 초기화.
                initializer.startUp();
            }, true, true);

        });
    }

    /**
     * 앱 최초 실행 및 번들 데이터 설치 테스트
     */
    @Test
    @DisplayName("앱 번들 데이터 설치 테스트")
    public void setupBundleDatasetTest() {

        Assertions.assertDoesNotThrow(() -> {
            TestContext.run((path, appService) -> {

                AppInitializer initializer = appService.getInitializer();

                //어플 초기화.
                initializer.startUp();

                //테스트용 번들 데이터 설치.
                initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                Integer wordCount = appService.getWordDicMngService()
                        .countWordByTopic(BundleDataSet.CMN_STN_TRM_6TH.getTopicId());
                assert wordCount > 0;
            });

        });
    }
}