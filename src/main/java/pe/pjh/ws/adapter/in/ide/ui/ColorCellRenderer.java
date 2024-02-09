package pe.pjh.ws.adapter.in.ide.ui;

import com.intellij.ui.JBColor;
import pe.pjh.ws.adapter.in.ide.ui.WordMngWordByTopicTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        switch (((WordMngWordByTopicTableModel)table.getModel()).getWordState(row)){
            case ADD -> cell.setBackground(JBColor.BLUE);
            case CHANGE -> cell.setBackground(JBColor.YELLOW);
            case DELETE -> {
                if (cell instanceof JLabel label && value instanceof String) {
                    label.setText("<html><strike>" + value + "</strike></html>");
                }
                cell.setBackground(JBColor.YELLOW);
            }
            default -> cell.setBackground(JBColor.WHITE);
        }

        return cell;
    }
}
