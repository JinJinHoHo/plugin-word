package pe.pjh.ws.adapter.out.datasource;

import com.couchbase.lite.*;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetType;
import pe.pjh.ws.application.service.setting.DataSetSetting;
import pe.pjh.ws.util.ExecuterParam1;
import pe.pjh.ws.util.ExecuterParam2;

import java.util.concurrent.atomic.AtomicInteger;

public class LocalCouchbaseDataSource implements DataSource {

    private static final Logger log = Logger.getInstance(LocalCouchbaseDataSource.class);

    DataSetSetting.SourceSetting sourceSetting;

    private boolean connected = false;

    DatabaseConfiguration config;

    public LocalCouchbaseDataSource(DataSetSetting.SourceSetting dataSourceSetting) {
        this.sourceSetting = dataSourceSetting;
    }


    public void execute(ExecuterParam1<Database> execute) throws Exception {
        try (Database database = new Database(sourceSetting.getDatabaseName(), config)) {
            execute.execute(database);
        }
    }

    public Document execute(ExecuterParam2<Document, Database> executerParam2) throws Exception {
        try (Database database = new Database(sourceSetting.getDatabaseName(), config)) {
            return executerParam2.execute(database);
        }
    }

    public void executeBatch(ExecuterParam1<Database> executerParam2) throws Exception {
        try (Database database = new Database(sourceSetting.getDatabaseName(), config)) {
            executerParam2.execute(database);
        }
    }

    @Override
    public String getDataSourceName() {
        return sourceSetting.getDatabaseName();
    }

    @Override
    public boolean isConnection() {
        return connected;
    }

    @Override
    public boolean connection() {
        if (connected) return connected;

        CouchbaseLite.init();

        config = new DatabaseConfiguration();
        config.setDirectory(sourceSetting.getPath());
        connected = true;
        return connected;

    }

    @Override
    public boolean isDataInit() throws Exception {

        //필요 테이블 갯수 설정.
        AtomicInteger checkCount = new AtomicInteger(DataSetType.values().length);

        //존재하고 있틑 테이블 갯수 차감.
        this.execute(database -> {
            for (DataSetType dataSetType : DataSetType.values()) {

                if (database.getCollection(dataSetType.getName()) == null) continue;

                checkCount.incrementAndGet();
            }
        });

        //값이 0이면 전부 존재한다고 판단 처리.
        return checkCount.get() == 0;
    }

    @Override
    public void dataInit() throws Exception {
        this.execute(database -> {
            log.info("create database : " + database.getName());

            Collection topicCollection = database.getCollection(DataSetType.topic.getName());
            if (topicCollection == null) {
                //컬렉션 생성.
                topicCollection = database.createCollection(DataSetType.topic.getName());
            }

            Collection wordCollection = database.getCollection(DataSetType.word.getName());
            if (wordCollection == null) {
                //컬렉션 생성.
                wordCollection = database.createCollection(DataSetType.word.getName());
                wordCollection.createIndex(
                        "names",
                        IndexBuilder.valueIndex(ValueIndexItem.property("names"))
                );
            }
        });
    }
}
