package pe.pjh.ws.application.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.WDException;
import pe.pjh.ws.application.service.setting.SettingService;

import java.io.File;

@Service
public final class Initialize {

    private static final Logger LOG = Logger.getInstance(Initialize.class);

    SettingService settingService;
    DataSetManager dataSetManager;

    public Initialize() {
        this(SettingService.getInstance(), DataSetManager.getInstance());
    }

    public Initialize(SettingService settingService, DataSetManager dataSetManager) {

        this.settingService = settingService;
        this.dataSetManager = dataSetManager;

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
        File dir = new File(settingService.getHomePath());
        return !dir.exists();
    }

    /**
     * 플러그인 초기화.
     * <p>디렉토리 생성 작업.<p/>
     */
    private void pluginInit() {
        File dir = new File(settingService.getHomePath());
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
        File dir = new File(settingService.getHomePath());

        if (!dir.canWrite()) {
            LOG.warn(dir + " 디렉토리 권한 없음.");
            throw new WDException("플러그인 홈 디렉토리 접근 권한 에러.");
        }


        //데이터 소스 설정
        settingService.getDataSetSettings().forEach(setting -> {
            DataSetManager.DataSourceRepository dataSourceRepository = dataSetManager.makeDateSet(setting);
            try {
                DataSource dataSource = dataSourceRepository.dataSource();
                if (!dataSource.isConnection()) {
                    if (!dataSource.connection()) {
                        throw new WDException(dataSource.getDataSourceName() + " 데이터 소스에 연결 또는 접근 안됨.");
                    }
                }
                if (!dataSource.isDataInit()) {
                    dataSource.dataInit();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }


}
