package pe.pjh.ws.application.service.setting;

import java.nio.file.Path;

public class DataSetSetting {

    public static final String DEFAULT_DATABASE_NAME = "_wordic";

    private final String dataSetName;

    private final SourceSetting sourceSetting;


    public DataSetSetting(String dataSetName, SourceSetting sourceSetting) {
        this.dataSetName = dataSetName;
        this.sourceSetting = sourceSetting;
    }


    public String getDataSetName() {
        return dataSetName;
    }

    public SourceSetting getSourceSetting() {
        return sourceSetting;
    }

    public static class SourceSetting {

        public SourceSetting(Path path, SourceType sourceType) {
            this(path, DEFAULT_DATABASE_NAME, sourceType);
        }

        public SourceSetting(Path path, String databaseName, SourceType sourceType) {
            this.path = path;
            this.databaseName = databaseName;
            this.sourceType = sourceType;
        }

        private final Path path;

        private final String databaseName;

        private final SourceType sourceType;

        public Path getPath() {
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
