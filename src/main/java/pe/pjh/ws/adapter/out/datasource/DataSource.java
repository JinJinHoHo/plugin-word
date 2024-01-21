package pe.pjh.ws.adapter.out.datasource;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import pe.pjh.ws.util.ExecuterParam1;
import pe.pjh.ws.util.ExecuterReturnParam2;

/**
 * 용어 데이터소스 프로세스
 *
 * <ul>
 *  <li>연결</li>
 *  <li>데이터 초기화</li>
 *  <li>데이터 조회</li>
 *  <li>데이터 저장</li>
 * </ul>
 */
public interface DataSource {

    String getDataSourceName();


    /**
     * 연결 여부 확인.
     *
     * @return
     */
    boolean isConnection();

    /**
     * 연결.
     */
    boolean connection();

    /**
     * 데이터 초기화 여부
     *
     * @return
     */
    boolean isDataInit() throws Exception;

    /**
     * 데이터 초기화.
     */
    void dataInit() throws Exception;

    void execute(ExecuterParam1<Database> execute) throws Exception;

    Document execute(ExecuterReturnParam2<Database, Document> executerParam2) throws Exception;

    void executeBatch(ExecuterParam1<Database> executerParam2) throws CouchbaseLiteException;

    <T> T execute(ExecuterReturnParam2<Database, T> executerParam2, Class<T> t1) throws CouchbaseLiteException;
}
