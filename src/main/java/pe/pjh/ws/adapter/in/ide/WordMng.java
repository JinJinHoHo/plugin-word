package pe.pjh.ws.adapter.in.ide;

import com.intellij.icons.AllIcons;

import javax.swing.*;

public class WordMng {
    private JTable wordMngTable;
    private JPanel basePanel;
    private JTabbedPane topicTabbed;

    public WordMng() {

        topicTabbed.addTab("+", null, new JPanel(), "토픽 추가");
        wordMngTable.getTableHeader().setReorderingAllowed(false);
        wordMngTable.setModel(new WordMngTableModel());
    }

    public JPanel getBasePanel() {
        return basePanel;
    }
}
