package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.dataset.Condition;
import pe.pjh.ws.application.service.dataset.Pagination;
import pe.pjh.ws.application.service.dataset.Word;
import pe.pjh.ws.util.ExecuterParam1;
import pe.pjh.ws.util.ExecuterReturnParam1;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 단어 관리 하위 토픽별 단어 테이블 모델
 */
public class WordMngWordByTopicTableModel extends AbstractTableModel {

    private final String[] columnNames = {"단어", "한글명", "영문명"};
    private final List<String[]> data = new ArrayList<>();

    final Integer topicNo;
    final String tabTitle;

    private Condition condition = null;
    private Pagination currentPagination = new Pagination(1);


    public WordMngWordByTopicTableModel(BundleDataSet bundleDataSet) {
        this(bundleDataSet.getTopicNo(), bundleDataSet.getName());
    }

    public WordMngWordByTopicTableModel(Integer topicNo, String tabTitle) {
        this.topicNo = topicNo;
        this.tabTitle = tabTitle;
    }

    public Integer getTopicNo() {
        return topicNo;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public void resetData() {

        Pagination pagination = new Pagination(1);

        //기존 데이터 초기화
        data.clear();

        loadData(null, pagination);
    }

    public void findData(String keyword) {

        Pagination pagination = new Pagination(1);

        //기존 데이터 초기화
        data.clear();

        loadData(new Condition(keyword), pagination);
    }

    public void moreData() {


        Pagination pagination = currentPagination != null
                ? new Pagination(currentPagination.getPageNumber() + 1)
                : new Pagination(1);

        loadData(null, pagination);
    }

    private synchronized void loadData(Condition condition, Pagination pagination) {

        ApplicationManager.getApplication().invokeLater(() -> {

            List<Word> wordList = AppService.getInstance().getWordDicMngService()
                    .findByTopic(topicNo, condition, pagination);

            if (!wordList.isEmpty()) {
                data.addAll(wordList.stream()
                        .map(word -> new String[]{
                                word.getWord(),
                                String.join(",", word.getNames()),
                                word.getEnglName()})
                        .toList());
                this.currentPagination = pagination;
                this.condition = condition;

                fireTableDataChanged();
            }
        });
    }
}