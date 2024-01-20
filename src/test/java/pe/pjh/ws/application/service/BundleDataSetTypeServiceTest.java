package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.application.Topic;
import pe.pjh.ws.application.Word;
import pe.pjh.ws.application.port.out.TopicBatchPort;
import pe.pjh.ws.application.port.out.WordBatchPort;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


class BundleDataSetTypeServiceTest {

    @Test
    void testLoadDataSet() {
        try {
            new BundleDataSetService().loadDataSet(BundleDataSet.CMN_STN_TRM_6TH,BundleDataSet.CMN_STN_TRM_6TH.getTopic());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}