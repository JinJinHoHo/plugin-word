package pe.pjh.ws.application.service;

import com.couchbase.lite.*;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;

@Service
public final class WordDicService {

    private static final Logger log = Logger.getInstance(WordDicService.class);

    DatabaseConfiguration config;

    public WordDicService() {


    }

    public void save() throws CouchbaseLiteException {
    }
}
