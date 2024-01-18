package pe.pjh.ws.adapter.out.datasource;

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
    void connection();

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
}
