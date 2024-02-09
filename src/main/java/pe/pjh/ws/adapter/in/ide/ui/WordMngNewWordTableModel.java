package pe.pjh.ws.adapter.in.ide.ui;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.BundleDataSet;
import pe.pjh.ws.application.service.dataset.Pagination;
import pe.pjh.ws.application.service.dataset.Word;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 단어 관리 하위 신규 단어 테이블 모델
 */
public class WordMngNewWordTableModel extends AbstractTableModel {

    private final List<Word> newWords = new ArrayList<>();
    private final String[] columnNames = {"단어", "한글명", "영문명","설명"};

    public WordMngNewWordTableModel() {
        newWords.add(new Word(null, "TEST","TEST",List.of("TEST"),"TEST"));
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return newWords.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {

        Word word = newWords.get(row);

        return switch (col) {
            case 0 -> word.getWord();
            case 1 -> String.join(",", word.getNames());
            case 2 -> String.join(",", word.getEnglName());
            case 3 -> String.join(",", word.getDescription());
            default -> null;
        };
    }
}