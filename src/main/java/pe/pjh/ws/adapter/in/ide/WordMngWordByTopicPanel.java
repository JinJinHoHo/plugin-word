package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WordMngWordByTopicPanel {

    private static final Logger log = Logger.getInstance(WordMngWordByTopicPanel.class);
    private JTable wordTable;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel basePanel;
    private JScrollPane tableScrollPane;
    private JTextField keyword;

    public WordMngWordByTopicPanel(WordMngWordByTopicTableModel model) {

        //테이블 추가 기본정보 설정.
        wordTable.getTableHeader().setReorderingAllowed(false);
        wordTable.setModel(model);
        wordTable.setDefaultRenderer(Object.class,new ColorCellRenderer());

        //이벤트 설정
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
                System.out.println("more fireTableDataChanged");
            } else {
                System.out.println("fireTableDataChanged");
            }

        });

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

                if (e.getKeyCode() != 10) return;

                String word = keyword.getText();
                model.findData(word);
            }
        });

        //등록 버튼 이벤트 설정.
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.addWord();
            }
        });

        //삭제 버튼 이벤트 설정
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.deleteWord();
            }
        });
//
//        wordTable.getSelectionModel().addListSelectionListener(event -> {
//            //Do something here
//            int selectedRow = jTable.getSelectedRow(); //Gets the selected row.
//            Object selectedObject = jTable.getValueAt(selectedRow, 0); //Gets the value of the first column of the selected row.
//        });

        //초기 데이터 설정.
        model.resetData();


    }

    private boolean locatedMaximumScrollPoint(JScrollBar verticalScrollBar) {


        int maxScrollPoint = verticalScrollBar.getMaximum() - verticalScrollBar.getHeight();
        int currentScrollPoint = verticalScrollBar.getValue();

        if (log.isDebugEnabled()) {
            log.debug(verticalScrollBar.getMaximum() + ":" + verticalScrollBar.getHeight() + ":" +
                      verticalScrollBar.getValue() + ":" + (maxScrollPoint == currentScrollPoint));
        }

        return maxScrollPoint == currentScrollPoint;
    }

    public JPanel getBasePanel() {
        return basePanel;
    }
}
