package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AppInitializeTest {

    private static final Logger LOG = Logger.getInstance(AppInitializeTest.class);

    /**
     * 초기화 플로우 테스트
     */
    @Test
    public void initializeTest() {

        try {
            TestContext.run((path, appService) -> {

                appService.plugInInitialize()
                        .init();

                assert FileUtil.exists(path.toString());
            }, true);

        } catch (IOException | CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }

    }
}