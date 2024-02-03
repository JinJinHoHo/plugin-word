package pe.pjh.ws.adapter.in.ide;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WordMngWordByTopicPanel {
    private JTable wordTable;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel basePanel;
    private JScrollPane tableScrollPane;
    private JTextField keyword;

    public WordMngWordByTopicPanel(WordMngWordByTopicTableModel model) {

        wordTable.getTableHeader().setReorderingAllowed(false);
        wordTable.setModel(model);

        //테이블 모델 변경(fireTableDataChanged 호출) 발생시 실행되는 이벤트
        model.addTableModelListener(e -> {

            JScrollBar verticalScrollBar = tableScrollPane.getVerticalScrollBar();

            /*
            UI 높이에 따라 추가 데이터 로딩
            - UI 세로 길이가 길었을때 리스팅 데이터가 화면을 채우지 못할 경우 재호출 스크롤이 생길때까지 화면을 채우도록 함.
            - 조회한 데이터가 있을 경우만 fireTableDataChanged 호출됨. 데이터가 없을 경우는 리스너가 호출되지 않음.
            */
            if (locatedMaximumScrollPoint(verticalScrollBar)) {
                model.moreData();
            }
        });

        //초기 데이터 설정.
        model.resetData();

        //세로 스크롤 이벤트 리스너 추가.
        tableScrollPane.getVerticalScrollBar()
                .addAdjustmentListener(e -> {

                    JScrollBar verticalScrollBar = (JScrollBar) e.getAdjustable();

                    //최대값이 아닌경우 반환.
                    if (!locatedMaximumScrollPoint(verticalScrollBar)) return;

                    //스크롤 끝에 왔을 경우 더보기 요청.
                    model.moreData();
                });


        keyword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == 10) {
                    String word = keyword.getText();
                    model.findData(word);
                } else {
                    System.out.println("keyReleased:" + e.getKeyChar() + ":" + e.getKeyCode());
                }

            }
        });
    }

    private boolean locatedMaximumScrollPoint(JScrollBar verticalScrollBar) {

        int maxScrollPoint = verticalScrollBar.getMaximum() - verticalScrollBar.getHeight();
        int currentScrollPoint = verticalScrollBar.getValue();

        return maxScrollPoint == currentScrollPoint;
    }

    public JPanel getBasePanel() {
        return basePanel;
    }
}
