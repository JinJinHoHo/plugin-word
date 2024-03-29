package pe.pjh.ws.adapter.out.datasource;

import com.couchbase.lite.*;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSetType;
import pe.pjh.ws.application.exception.QueryException;
import pe.pjh.ws.application.service.setting.ConnecterSetting;
import pe.pjh.ws.util.ExecuterParam1;
import pe.pjh.ws.util.ExecuterReturnParam2;

import java.util.concurrent.atomic.AtomicInteger;

public class LocalCouchbaseDataSource implements DataSource {

    private static final Logger log = Logger.getInstance(LocalCouchbaseDataSource.class);

    ConnecterSetting connecterSetting;

    private boolean connected = false;

    DatabaseConfiguration config;

    public LocalCouchbaseDataSource(ConnecterSetting dataConnecterSetting) {
        this.connecterSetting = dataConnecterSetting;
    }


    public void execute(ExecuterParam1<Database> execute) throws QueryException {
        try (Database database = new Database(connecterSetting.getDatabaseName(), config)) {
            execute.execute(database);
        }catch (CouchbaseLiteException e) {
            throw new QueryException(e);
        }
    }

    public Document execute(ExecuterReturnParam2<Database, Document> executerParam2) {
        try (Database database = new Database(connecterSetting.getDatabaseName(), config)) {
            return executerParam2.execute(database);
        } catch (CouchbaseLiteException e) {
            throw new QueryException(e);
        }
    }

    public void executeBatch(ExecuterParam1<Database> executerParam2) throws QueryException {
        try (Database database = new Database(connecterSetting.getDatabaseName(), config)) {
            executerParam2.execute(database);
        }catch (CouchbaseLiteException e) {
            throw new QueryException(e);
        }
    }

    public <T> T execute(ExecuterReturnParam2<Database, T> executerParam2, Class<T> t1) throws QueryException {
        try (Database database = new Database(connecterSetting.getDatabaseName(), config)) {
            return executerParam2.execute(database);
        } catch (CouchbaseLiteException e) {
            throw new QueryException(e);
        }
    }

    @Override
    public String getDataSourceName() {
        return connecterSetting.getDatabaseName();
    }

    @Override
    public boolean isConnection() {
        return connected;
    }

    @Override
    public boolean connection() {
        if (connected) return true;

        CouchbaseLite.init();

        config = new DatabaseConfiguration();
        config.setDirectory(connecterSetting.getPath().toString());
        connected = true;
        return true;

    }

    @Override
    public boolean isDataInit() throws Exception {

        //필요 테이블 갯수 설정.
        AtomicInteger checkCount = new AtomicInteger(DataSetType.values().length);

        //존재하고 있틑 테이블 갯수 차감.
        this.execute(database -> {
            for (DataSetType dataSetType : DataSetType.values()) {
                try {
                    if (database.getCollection(dataSetType.getName()) == null) continue;
                } catch (CouchbaseLiteException e) {
                    throw new RuntimeException(e);
                }
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

            try {
                Collection topicCollection = database.getCollection(DataSetType.topic.getName());
                if (topicCollection == null) {
                    //컬렉션 생성.
                    database.createCollection(DataSetType.topic.getName());
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
            } catch (CouchbaseLiteException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
