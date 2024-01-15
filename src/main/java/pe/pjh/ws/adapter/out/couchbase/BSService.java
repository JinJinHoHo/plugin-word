package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.*;
import com.intellij.openapi.diagnostic.Logger;

import java.net.URL;

public abstract class BSService {

    private static final Logger log = Logger.getInstance(BSService.class);

    DatabaseConfiguration config;

    public BSService() throws CouchbaseLiteException {

        CouchbaseLite.init();

        config = new DatabaseConfiguration();
        config.setDirectory("/Users/pjh/test");

        // 데이터베이스 생성s
        Document document;
        try (Database database = new Database("mydb", config)) {

            Collection collection = database.createCollection("myCollection", "myScope");
            System.out.println("Collection created: " + collection);

// 문서 생성 및 저장
            MutableDocument mutableDoc = new MutableDocument()
                    .setFloat("version", 2.0F)
                    .setString("type", "SDK");
            collection.save(mutableDoc);
            log.info("저장");

// 문서 읽기
            document = database.getDocument(mutableDoc.getId());
        }
        log.info("Document ID :: " + document.getId());

// JSON 형태
        log.info("Learning " + document.getString("type"));
    }

    public void pluginInit(){
        URL url = getClass().getResource("/resources/data/data_go_kr_cmn_stn_trm_6th_word.json");
    }

    public void saveDocument(String db, Execute<Document, Database> execute, String id, String key, Object value) throws CouchbaseLiteException {
        try (Database database = new Database(db, config)) {


            Collection collection = database.getCollection("myCollection", "myScope");
            Document document = collection.getDocument(id);
            if (document == null) return;
            MutableDocument mutableDoc = document.toMutable();
            mutableDoc.setValue(key, value);
            collection.save(mutableDoc);
            log.info("Document saved with ID: " + id);
        }
    }

    public Document saveDocument2(String db, Execute<Document, Database> execute) throws CouchbaseLiteException {
        try (Database database = new Database(db, config)) {
            return execute.execute(database);
        }
    }
}
