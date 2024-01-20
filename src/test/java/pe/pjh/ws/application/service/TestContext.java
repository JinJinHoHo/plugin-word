package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.util.io.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestContext {
    public static void run(ExecuterTestContext<Path, AppService> executer, boolean cleanDir) throws IOException, CouchbaseLiteException {


        //테스트 디렉토리
        String workingDirectory = System.getProperty("user.dir");
        String testPath = workingDirectory + "/.test";

        //기존 테스트 디렉토리 삭제.
        Path testWorkPath = Paths.get(workingDirectory, ".test");
        if (cleanDir) {
            if (FileUtil.exists(testWorkPath.toString())) FileUtil.delete(testWorkPath);
        }

        AppService appService = new AppService(testWorkPath);

//
//        StatusService statusService = appService.getStatusService();
//
//        //테스트 실행을 위한 기본 설정 추가화.
//        SettingService settingService = new SettingService();
//        settingService.setDataSetSettings(
//                List.of(new DataSetSetting(
//                                BundleDataSet.CMN_STN_TRM_6TH.getName(),
//                                new DataSetSetting.SourceSetting(statusService.getWorkPath(), SourceType.LocalCouchbaseLite)
//                        )
//                )
//        );
//        DataSetManager dataSetManager = new DataSetManager();

        executer.execute(testWorkPath, appService);
    }
}