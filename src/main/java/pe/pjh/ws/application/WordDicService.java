package pe.pjh.ws.application;

import com.couchbase.lite.*;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;

@Service(Service.Level.PROJECT)
public final class WordDicService {

    private static final Logger log = Logger.getInstance(WordDicService.class);

    DatabaseConfiguration config;

    public WordDicService() throws CouchbaseLiteException {

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

    public void save() throws CouchbaseLiteException {
        // 데이터베이스 생성s
        Document document;
        try (Database database = new Database("mydb", config)) {

// 문서 생성 및 저장
            MutableDocument mutableDoc = new MutableDocument()
                    .setFloat("version", 2.0F)
                    .setString("type", "SDK");
            database.save(mutableDoc);
            System.out.println("저장");

// 문서 읽기
            document = database.getDocument(mutableDoc.getId());
        }
        System.out.println("Document ID :: " + document.getId());

// JSON 형태
        System.out.println("Learning " + document.getString("type"));
    }
}
