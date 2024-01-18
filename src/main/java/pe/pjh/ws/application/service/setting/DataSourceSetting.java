package pe.pjh.ws.application.service.setting;

import pe.pjh.ws.application.service.SourceType;

public class DataSourceSetting {

    private String path = "/Users/pjh/test";

    private String sourceName = "WD";

    private SourceType sourceType = SourceType.LocalCouchbaseLite;

    public String getPath() {
        return path;
    }

    public String getSourceName() {
        return sourceName;
    }

    public SourceType getDataSourceType() {
        return sourceType;
    }
}
