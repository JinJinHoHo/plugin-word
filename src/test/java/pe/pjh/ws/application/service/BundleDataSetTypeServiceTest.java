package pe.pjh.ws.application.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;


class BundleDataSetTypeServiceTest {

    @Test
    void testLoadDataSet() {
        try {
            BundleDataSetLoder.load(BundleDataSet.CMN_STN_TRM_6TH,BundleDataSet.CMN_STN_TRM_6TH.getTopic());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}