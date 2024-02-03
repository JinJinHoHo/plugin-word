package pe.pjh.ws.application.service.dataset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.service.AppInitializer;
import pe.pjh.ws.application.service.TestContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordDicMngServiceTest {

    @Test
    void countWordByTopic() {
        Assertions.assertDoesNotThrow(() -> TestContext
                .run((path, appService) -> {

                    AppInitializer initializer = appService.getInitializer();

                    //어플 초기화.
                    initializer.startUp();

                    //테스트용 번들 데이터 설치.
                    initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                    assert appService.getWordDicMngService()
                                   .countWordByTopic(BundleDataSet.CMN_STN_TRM_6TH.topicNo) > 1;

                })
        );
    }

    @Test
    void findByTopic() {

        Assertions.assertDoesNotThrow(() -> TestContext
                .run((path, appService) -> {

                    AppInitializer initializer = appService.getInitializer();

                    //어플 초기화.
                    initializer.startUp();

                    //테스트용 번들 데이터 설치.
                    initializer.setupBundleDataset(BundleDataSet.CMN_STN_TRM_6TH);

                    List<Word> wordList = appService.getWordDicMngService()
                            .findByTopic(BundleDataSet.CMN_STN_TRM_6TH.topicNo,
                                    new Condition("회"),
                                    new Pagination());

                    assert wordList.size() > 1;
                })
        );
    }
}