package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 단어 관리 하위 토픽별 단어 테이블 모델
 */
public class WordMngWordByTopicTableModel extends AbstractTableModel {

    private final String[] columnNames = {"단어", "한글명", "영문명"};
    private final LinkedList<WordStateShell> storeWordDatas = new LinkedList<>();

    final Integer topicNo;
    final String tabTitle;

    private Condition condition = null;
    private Pagination currentPagination = new Pagination(1);


    public WordMngWordByTopicTableModel(BundleDataSet bundleDataSet) {
        this(bundleDataSet.getTopicNo(), bundleDataSet.getName());
    }

    public WordMngWordByTopicTableModel(Topic topic) {
        this(topic.getTopicNo(), topic.getTopicName());
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
        return storeWordDatas.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {

        WordStateShell wordStateShell = storeWordDatas.get(row);

        Word word = wordStateShell.getCurrentWord();

        return switch (col) {
            case 0 -> word.getWord();
            case 1 -> String.join(",", word.getNames());
            case 2 -> word.getEnglName();
            default -> throw new IllegalStateException("Unexpected value: " + col);
        };
    }

    public void addWord() {
        storeWordDatas.addFirst(
                new WordStateShell(new Word(topicNo, "", "", new ArrayList<>(), ""),
                        WordStatus.ADD));

        fireTableDataChanged();
    }

    public void deleteWord() {

    }

    public WordStatus getWordState(int row) {
        return storeWordDatas.get(row).getStatus();
    }

    public void resetData() {

        //기존 데이터 초기화
        storeWordDatas.clear();

        loadData(null, new Pagination(1), false);
    }

    public void findData(String keyword) {

        //기존 데이터 초기화
        storeWordDatas.clear();

        loadData(new Condition(keyword), new Pagination(1), false);
    }

    public void moreData() {

        Pagination pagination = currentPagination != null
                ? new Pagination(currentPagination.getPageNumber() + 1)
                : new Pagination(1);

        loadData(condition, pagination, true);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * 데이터 로딩
     * <p>fireTableDataChanged 호출 하는 경우</p>
     *
     * @param condition
     * @param pagination
     */
    private synchronized void loadData(Condition condition, Pagination pagination, boolean more) {

        ApplicationManager.getApplication().invokeLater(() -> {

            List<Word> wordList = AppService.getInstance().getWordDicMngService()
                    .findByTopic(topicNo, condition, pagination);

            this.condition = condition;

            if (wordList.isEmpty() && more) return;

            this.currentPagination = pagination;

            storeWordDatas.addAll(wordList.stream().map(WordStateShell::new).toList());

            fireTableDataChanged();
        });
    }
}