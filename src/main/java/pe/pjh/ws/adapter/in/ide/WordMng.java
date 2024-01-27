package pe.pjh.ws.adapter.in.ide;

import javax.swing.*;

public class WordMng extends JPanel {
    private JTable wordMngTable;
    private JPanel basePanel;

    public WordMng() {
        add(basePanel);

        wordMngTable.getTableHeader().setReorderingAllowed(false);
        wordMngTable.setModel(new WordMngTableModel());
    }


}
