package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import org.junit.jupiter.api.Test;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;
import pe.pjh.ws.util.ExecuterParam2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PlugInInitializeTest {

    private static final Logger LOG = Logger.getInstance(PlugInInitializeTest.class);

    /**
     * 초기화 플로우 테스트
     */
    @Test
    public void initializeTest() {

        try {
            TestContext.run((path, settingService, dataSetManager) -> {
                //초기화 처리.
                BundleDataSetService bundleDataSetService = new BundleDataSetService();
                PlugInInitialize plugInInitialize = new PlugInInitialize(settingService, dataSetManager, bundleDataSetService);
                plugInInitialize.init();

                assert FileUtil.exists(path.toString());
            },true);

        } catch (IOException | CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }

    }
}