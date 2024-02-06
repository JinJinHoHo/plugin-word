package pe.pjh.ws.application.service.setting;

import java.nio.file.Path;

/**
 * 커넥터 설정
 */
public class ConnecterSetting {

    public static final String DEFAULT_DATABASE_NAME = "_wordic";

    public ConnecterSetting(Path path, ConnecterSourceType connecterSourceType) {
        this(path, DEFAULT_DATABASE_NAME, connecterSourceType);
    }

    public ConnecterSetting(Path path, String databaseName, ConnecterSourceType connecterSourceType) {
        this.path = path;
        this.databaseName = databaseName;
        this.connecterSourceType = connecterSourceType;
    }

    private final Path path;

    private final String databaseName;

    private final ConnecterSourceType connecterSourceType;

    public Path getPath() {
        return path;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public ConnecterSourceType getDataSourceType() {
        return connecterSourceType;
    }
}
