package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.dataset.Pagination;
import pe.pjh.ws.application.service.dataset.Word;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 단어 관리 하위 토픽별 단어 테이블 모델
 */
public class WordMngWordByTopicTableModel extends AbstractTableModel {
    private final String[] columnNames = {"단어", "한글명", "영문명"};
    private List<String[]> data = new ArrayList<>();

    final Integer topicNo;
    final String tabTitle;

    public WordMngWordByTopicTableModel(BundleDataSet bundleDataSet) {
        this(bundleDataSet.getTopicNo(), bundleDataSet.getName());
    }

    public WordMngWordByTopicTableModel(Integer topicNo, String tabTitle) {
        this.topicNo = topicNo;
        this.tabTitle = tabTitle;
        refresh();
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

    public void refresh() {
        ApplicationManager.getApplication().invokeLater(() -> {
            List<Word> wordList = AppService.getInstance().getWordDicMngService()
                    .findByTopic(topicNo, new Pagination());

            data = wordList.stream()
                    .map(word -> new String[]{
                            word.getWord(),
                            String.join(",", word.getNames()),
                            word.getEnglName()})
                    .toList();

            fireTableDataChanged();
        });
    }
}