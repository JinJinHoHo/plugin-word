package pe.pjh.ws.application.service;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DatabaseConfiguration;
import com.intellij.openapi.diagnostic.Logger;

public class WordDicMngService {

    private static final Logger log = Logger.getInstance(WordDicMngService.class);

    DatabaseConfiguration config;

    public WordDicMngService() {


    }

    public void save() throws CouchbaseLiteException {
    }
}
