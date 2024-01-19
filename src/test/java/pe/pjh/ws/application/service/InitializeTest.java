package pe.pjh.ws.application.service;

import org.junit.jupiter.api.Test;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class InitializeTest {

    @Test
    public void initializeTest() {
        String workingDirectory = System.getProperty("user.dir");
        String testPath = workingDirectory + "/.test";
        File file = new File(testPath);
        if (file.exists()) file.delete();

        SettingService settingService = new SettingService(
                testPath,
                List.of(new DataSetSetting())
        );

        DataSetManager dataSetManager = new DataSetManager();

        Initialize initialize = new Initialize(settingService, dataSetManager);

        assert file.exists();
    }
}