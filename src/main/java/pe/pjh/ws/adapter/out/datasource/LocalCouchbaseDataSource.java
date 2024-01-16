package pe.pjh.ws.adapter.out.datasource;

import com.couchbase.lite.*;
import com.intellij.openapi.diagnostic.Logger;
import pe.pjh.ws.adapter.out.DataSet;
import pe.pjh.ws.util.ExecuterParam1;
import pe.pjh.ws.util.ExecuterParam2;
import pe.pjh.ws.application.service.DataSourceSetting;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalCouchbaseDataSource implements DataSource {

    private static final Logger log = Logger.getInstance(LocalCouchbaseDataSource.class);

    DataSourceSetting dataSourceSetting;

    private boolean connected = false;

    DatabaseConfiguration config;

    public LocalCouchbaseDataSource(DataSourceSetting dataSourceSetting) {
        this.dataSourceSetting = dataSourceSetting;

        connection();

    }

    public void pluginInit() {
        URL url = getClass().getResource("/resources/data/data_go_kr_cmn_stn_trm_6th_word.json");
    }

    public Document execute(ExecuterParam2<Document, Database> executerParam2) throws CouchbaseLiteException {
        try (Database database = new Database(dataSourceSetting.getDatabaseName(), config)) {
            return executerParam2.execute(database);
        }
    }

    public void execute(ExecuterParam1<Database> execute) throws Exception {
        try (Database database = new Database(dataSourceSetting.getDatabaseName(), config)) {
            execute.execute(database);
        }
    }

    @Override
    public boolean isConnection() {
        return connected;
    }

    @Override
    public void connection() {
        if (connected) return;

        CouchbaseLite.init();
        config = new DatabaseConfiguration();
        config.setDirectory(dataSourceSetting.getPath());
        connected = true;

    }

    @Override
    public boolean isDataInit() throws Exception {

        //필요 테이블 갯수 설정.
        AtomicInteger checkCount = new AtomicInteger(DataSet.values().length);

        //존재하고 있틑 테이블 갯수 차감.
        this.execute(database -> {
            for (DataSet coll : DataSet.values()) {
                if (database.getCollection(coll.getName()) == null) continue;
                checkCount.getAndDecrement();
            }
        });

        //값이 0이면 전부 존재한다고 판단 처리.
        return checkCount.get() == 0;
    }

    @Override
    public void dataInit() throws Exception {
        this.execute(database -> {
            for (DataSet coll : DataSet.values()) {

                // 미생성 컬렉션 확인.
                if (database.getCollection(coll.getName()) != null) continue;

                //컬렉션 생성.
                database.createCollection(coll.getName());
            }
        });
    }
}
