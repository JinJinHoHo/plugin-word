package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class WordMngToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {



        Content content = ContentFactory.getInstance()
                .createContent(new WordMng(), "", false);

        toolWindow.getContentManager()
                .addContent(content);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Hello, world!");
        JButton button = new JButton("Click me!");
        button.addActionListener(e -> label.setText("Button clicked!"));
        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);
        return panel;
    }
}
