package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.util.io.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestContext {
    public static void run(ExecuterTestContext<Path, AppService> executer, boolean beforeCleanDir, boolean afterCleanDir)
            throws IOException, CouchbaseLiteException {


        //테스트 디렉토리
        String workingDirectory = System.getProperty("user.dir");
        String testPath = workingDirectory + "/.test";

        //기존 테스트 디렉토리 삭제.
        Path testWorkPath = Paths.get(workingDirectory, ".test");
        if (beforeCleanDir) {
            if (FileUtil.exists(testWorkPath.toString())) FileUtil.delete(testWorkPath);
        }

        AppService appService = new AppService(testWorkPath);

        executer.execute(testWorkPath, appService);

        if (afterCleanDir) {
            if (FileUtil.exists(testWorkPath.toString())) FileUtil.delete(testWorkPath);
        }
    }

    public static void run(ExecuterTestContext<Path, AppService> executer)
            throws IOException, CouchbaseLiteException {


        run(executer, false, false);
    }
}