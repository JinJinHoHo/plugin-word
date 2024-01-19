package pe.pjh.ws.application.service.setting;

import pe.pjh.ws.application.service.SourceType;

public class DataSetSetting {

    public static final String DEFAULT_DATABASE_NAME = "_wordic";

    private String dataSetName;

    private SourceSetting sourceSetting;

    public DataSetSetting() {
        //Todo : 삭제 예정. 개발시 사용 하는 임시용.
        this("WD", new SourceSetting());
    }

    public DataSetSetting(String dataSetName, SourceSetting sourceSetting) {
        this.dataSetName = dataSetName;
        this.sourceSetting = sourceSetting;
    }


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

    public static class SourceSetting {

        public SourceSetting() {
            //Todo : 삭제 예정. 개발시 사용 하는 임시용.
            this("/Users/pjh/test", SourceType.LocalCouchbaseLite);
        }

        public SourceSetting(String path, SourceType sourceType) {
            this(path, DEFAULT_DATABASE_NAME, sourceType);
        }

        public SourceSetting(String path, String databaseName, SourceType sourceType) {
            this.path = path;
            this.databaseName = databaseName;
            this.sourceType = sourceType;
        }

        private final String path;

        private final String databaseName;

        private final SourceType sourceType;

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
