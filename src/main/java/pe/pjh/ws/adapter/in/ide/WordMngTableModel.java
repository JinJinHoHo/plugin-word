package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.application.ApplicationManager;
import pe.pjh.ws.application.service.AppService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WordMngTableModel extends AbstractTableModel {
    private final String[] columnNames = {"단어", "한글명", "영문명"};
    private final List<String[]> data = List.of(
            new String[]{"Kathy", "Smith", "Snowboarding"},
            new String[]{"John", "Doe", "Rowing"}
    );

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
            AppService.getInstance().getWordDicMngService();
        });
    }
}