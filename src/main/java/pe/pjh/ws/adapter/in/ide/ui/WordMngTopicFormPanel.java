package pe.pjh.ws.adapter.in.ide.ui;

import javax.swing.*;
import java.awt.event.*;

public class WordMngTopicFormPanel {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;

    public WordMngTopicFormPanel() {

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // ESCAPE 시 onCancel() 호출
        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void onOK() {
        // 이곳에 코드 추가

    }

    private void onCancel() {
        // 필요한 경우 이곳에 코드 추가

    }

}
