package pe.pjh.ws.application.service.setting;

/**
 * 데이터 소스 설정
 */
public class DataSourceSetting {

    private final String dataSetName;

    private final ConnecterSetting connecterSetting;

    public DataSourceSetting(String dataSetName, ConnecterSetting connecterSetting) {
        this.dataSetName = dataSetName;
        this.connecterSetting = connecterSetting;
    }


    public String getDataSetName() {
        return dataSetName;
    }

    public ConnecterSetting getSourceSetting() {
        return connecterSetting;
    }

}
