package pe.pjh.ws.application.service;

import org.junit.jupiter.api.Test;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class InitializeTest {

    /**
     * 초기화 플로우 테스트
     */
    @Test
    public void initializeTest() {

        //테스트 디렉토리
        String workingDirectory = System.getProperty("user.dir");
        String testPath = workingDirectory + "/.test";

        //기존 테스트 디렉토리 삭제.
        File file = new File(testPath);
        if (file.exists()) file.delete();

        //테스트를 위한 설정 값.
        SettingService settingService = new SettingService(
                testPath,
                List.of(new DataSetSetting())
        );
        DataSetManager dataSetManager = new DataSetManager();

        //초기화 처리.
        Initialize initialize = new Initialize(settingService, dataSetManager);

        //Todo 추가 테스트 로직 정의 필요.
        assert file.exists();
    }

}