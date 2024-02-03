package pe.pjh.ws.adapter.in.ide;

import javax.swing.*;

public class RootPanel {
    private JTabbedPane topicTabbed;
    private JPanel basePanel;


    public RootPanel(WordMngModel wordMngModel) {

        WordMngNewWordPanel wordMngNewWordPanel = new WordMngNewWordPanel();
        wordMngNewWordPanel.getNewWordTable().getTableHeader().setReorderingAllowed(false);
        wordMngNewWordPanel.getNewWordTable().setModel(wordMngModel.getWordMngNewWordTableModel());
        topicTabbed.addTab("신규단어", null, wordMngNewWordPanel.getBasePanel(), "토픽 추가");

        wordMngModel.getWordsByTopicTableModels()
                .forEach(wordsByTopicTableModel -> {

                    WordMngWordByTopicPanel wordMngWordByTopicPanel = new WordMngWordByTopicPanel(wordsByTopicTableModel);

                    topicTabbed.addTab(
                            wordsByTopicTableModel.getTabTitle(),
                            null,
                            wordMngWordByTopicPanel.getBasePanel(),
                            wordsByTopicTableModel.getTabTitle());
                });

        topicTabbed.addTab("+", null, new JPanel(), "토픽 추가");
    }

    public JPanel getBasePanel() {
        return basePanel;
    }
}
