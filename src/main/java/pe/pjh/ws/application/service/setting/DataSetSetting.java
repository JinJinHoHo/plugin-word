package pe.pjh.ws.application.service.setting;

import pe.pjh.ws.application.service.SourceType;

public class DataSetSetting {

    private String dataSetName = "WD";

    private SourceSetting sourceSetting = new SourceSetting();

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public SourceSetting getSourceSetting() {
        return sourceSetting;
    }

    public void setSourceSetting(SourceSetting sourceSetting) {
        this.sourceSetting = sourceSetting;
    }

    public class SourceSetting {

        private String path = "/Users/pjh/test";

        private String databaseName = "WD";

        private SourceType sourceType = SourceType.LocalCouchbaseLite;

        public String getPath() {
            return path;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public SourceType getDataSourceType() {
            return sourceType;
        }
    }
}
