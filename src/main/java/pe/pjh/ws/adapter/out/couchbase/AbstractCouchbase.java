package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.Collection;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import pe.pjh.ws.adapter.out.datasource.DataSource;

public abstract class AbstractCouchbase {
    final DataSource dataSource;

    public AbstractCouchbase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Collection getCollection(Database database) throws CouchbaseLiteException {
        return database.getCollection(getCollectionName());
    }

    protected abstract String getCollectionName();
}
