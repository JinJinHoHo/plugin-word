package pe.pjh.ws.application.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.Topic;
import pe.pjh.ws.application.WDException;
import pe.pjh.ws.application.Word;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.application.service.setting.SettingService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public final class PlugInInitialize {

    private static final Logger LOG = Logger.getInstance(PlugInInitialize.class);

    SettingService settingService;
    DataSetManager dataSetManager;

    BundleDataSetService bundleDataSetService;

    public static SettingService getInstance() {
        return ApplicationManager.getApplication().getService(SettingService.class);
    }

    public PlugInInitialize() {
        this(SettingService.getInstance(), DataSetManager.getInstance(), BundleDataSetService.getInstance());
    }

    public PlugInInitialize(SettingService settingService, DataSetManager dataSetManager, BundleDataSetService bundleDataSetService) {
        this.settingService = settingService;
        this.dataSetManager = dataSetManager;
        this.bundleDataSetService = bundleDataSetService;
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
        File dir = new File(settingService.getWorkPath());
        return !dir.exists();
    }

    /**
     * 플러그인 초기화.
     * <p>디렉토리 생성 작업.<p/>
     */
    private void pluginInit() {
        File dir = new File(settingService.getWorkPath());
        if (dir.exists()) return;

        if (dir.mkdirs()) return;

        throw new WDException("플러그인 홈 디렉토리 생성 오류.");
        // 디렉토리 생성에 실패했습니다. 권한 문제가 있을 수 있습니다.
    }

    /**
     * 플러그인 스타트업
     * <p>ide 실행후 초기화 실행, 데이터셋 관련 정보 처리가 주로직.</p>
     */
    private void pluginStartUp() {
        File dir = new File(settingService.getWorkPath());

        if (!dir.canWrite()) {
            LOG.warn(dir + " 디렉토리 권한 없음.");
            throw new WDException("플러그인 홈 디렉토리 접근 권한 에러.");
        }


        //데이터 소스 설정
        settingService.getDataSetSettings()
                .forEach(dataSetSetting -> {
                            DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(dataSetSetting);
                            try {
                                DataSource dataSource = dataSet.dataSource();
                                if (!dataSource.isConnection()) {
                                    if (!dataSource.connection()) {
                                        throw new WDException(dataSource.getDataSourceName() + "이 설정된 데이터 소스에 연결 또는 접근 안됨.");
                                    }
                                }
                                if (!dataSource.isDataInit()) {
                                    dataSource.dataInit();
                                }

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                setupBundleDataset(dataSetSetting, BundleDataSet.CMN_STN_TRM_6TH);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                );


    }

    private void setupBundleDataset(DataSetSetting dataSetSetting, BundleDataSet bundleDataSet) throws Exception {
        DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(dataSetSetting);
        DataSource dataSource = dataSet.dataSource();

        Topic topic = bundleDataSet.getTopic();
        List<Word> words;
        try {
            words = bundleDataSetService.loadDataSet(bundleDataSet, topic);
        } catch (URISyntaxException | IOException e) {
            LOG.info(bundleDataSet + " 설정된 리소스 오류.",e);
            throw new WDException(bundleDataSet + " 설정된 리소스 오류.");
        }

        dataSource.executeBatch(database -> {
            dataSet.topicRepository().createTopic(database, topic);
            dataSet.wordRepository().createWords(database, words);
        });

    }
}
