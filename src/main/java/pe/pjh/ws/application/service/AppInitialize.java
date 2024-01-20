package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.Topic;
import pe.pjh.ws.application.WDException;
import pe.pjh.ws.application.Word;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class AppInitialize {

    private static final Logger LOG = Logger.getInstance(AppInitialize.class);

    SettingService settingService;

    StatusService statusService;

    DataSetManager dataSetManager;


    public AppInitialize(SettingService settingService,
                         DataSetManager dataSetManager,
                         StatusService statusService
    ) {
        this.settingService = settingService;
        this.dataSetManager = dataSetManager;
        this.statusService = statusService;
    }

    public void init() {
        if (isPluginInitialize()) {
            pluginInit();
        }
        pluginStartUp();
    }

    /**
     * 플러그인 초기화 여부
     * <p>플러그인 홈디렉토리 존재 여부 확인.</p>
     *
     * @return
     */
    private boolean isPluginInitialize() {
        return !Files.exists(statusService.getWorkPath());
    }

    /**
     * 플러그인 초기화.
     * <p>디렉토리 생성 작업.<p/>
     */
    private void pluginInit() {
        if (Files.exists(statusService.getWorkPath())) return;

        try {
            Files.createDirectories(statusService.getWorkPath());
        } catch (IOException e) {
            throw new WDException("플러그인 홈 디렉토리 생성 오류.");
        }
        // 디렉토리 생성에 실패했습니다. 권한 문제가 있을 수 있습니다.
    }

    /**
     * 플러그인 스타트업
     * <p>ide 실행후 초기화 실행, 데이터셋 관련 정보 처리가 주로직.</p>
     */
    private void pluginStartUp() {

        if (!Files.isWritable(statusService.getWorkPath())) {
            LOG.warn(statusService.getWorkPath() + " 디렉토리 권한 없음.");
            throw new WDException("플러그인 홈 디렉토리 접근 권한 에러.");
        }

        //데이터 소스 설정
        DataSetSetting dataSetSetting = statusService.getCurrentDataSetSetting();
        DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(dataSetSetting);
        try {
            DataSource dataSource = dataSet.dataSource();

            if (!dataSource.isConnection()) {
                if (!dataSource.connection()) {
                    throw new WDException(dataSource.getDataSourceName() + "이 설정된 데이터 소스에 연결 또는 접근 안됨.");
                }
            }

            if (!dataSource.isDataInit()) dataSource.dataInit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            setupBundleDataset(dataSetSetting, BundleDataSet.CMN_STN_TRM_6TH);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupBundleDataset(DataSetSetting dataSetSetting, BundleDataSet bundleDataSet) throws Exception {

        Topic topic = bundleDataSet.getTopic();
        List<Word> words;
        try {
            words = BundleDataSetLoder.load(bundleDataSet, topic);
        } catch (URISyntaxException | IOException e) {
            LOG.info(bundleDataSet + " 설정된 리소스 오류.", e);
            throw new WDException(bundleDataSet + " 설정된 리소스 오류.");
        }

        DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(dataSetSetting);
        dataSet.dataSource()
                .executeBatch(database -> {
                            try {
                                database.inBatch(() -> {
                                            dataSet.topicRepository().createTopic(database, topic);
                                            dataSet.wordRepository().createWords(database, words);
                                        });
                            } catch (CouchbaseLiteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

    }
}
