package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.*;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.exception.DataSourceException;

public abstract class AbstractCouchbase {


    final DataSource dataSource;

    public AbstractCouchbase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Collection getCollection(Database database) {
        try {
            return database.getCollection(getCollectionName());
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }

    protected abstract String getCollectionName();
}
