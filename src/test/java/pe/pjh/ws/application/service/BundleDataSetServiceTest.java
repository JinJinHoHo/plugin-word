package pe.pjh.ws.application.service;

import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.Word;
import pe.pjh.ws.application.port.out.TopicBatchPort;
import pe.pjh.ws.application.port.out.WordBatchPort;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


class BundleDataSetServiceTest {

    @Test
    void testLoadDataSet() {
        try {
            new BundleDataSetService(
                    new TopicBatchPort() {

                    },
                    new WordBatchPort() {
                        @Override
                        public void processBatch(List<Word> words) {

                        }
                    }
            ).loadDataSet(BundleDataSet.CMN_STN_TRM_6TH);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}