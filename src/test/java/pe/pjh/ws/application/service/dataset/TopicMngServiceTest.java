package pe.pjh.ws.application.service.dataset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.service.AppInitializer;
import pe.pjh.ws.application.service.TestContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopicMngServiceTest {

    @Test
    @DisplayName("토픽 조회 동작 확인")
    void findByTopic() {
        Assertions.assertDoesNotThrow(() -> TestContext
                .run((path, appService) -> {

                    AppInitializer initializer = appService.getInitializer();

                    //어플 초기화.
                    initializer.startUp();

                    //테스트용 번들 데이터 설치.
                    initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                    List<Topic> wordList = appService.getTopicMngService()
                            .findByTopic();

                    assert !wordList.isEmpty();
                })
        );
    }
}