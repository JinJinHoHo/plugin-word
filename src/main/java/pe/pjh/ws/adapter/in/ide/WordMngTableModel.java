package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.dataset.Pagination;
import pe.pjh.ws.application.service.dataset.Word;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordMngTableModel extends AbstractTableModel {
    private final String[] columnNames = {"단어", "한글명", "영문명"};
    private List<String[]> data = new ArrayList<>();

    public WordMngTableModel() {
        refresh();
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
                    .findByTopic(
                            BundleDataSet.CMN_STN_TRM_6TH.getTopicNo(), new Pagination());

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