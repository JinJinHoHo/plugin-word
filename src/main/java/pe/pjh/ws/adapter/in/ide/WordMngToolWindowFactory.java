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

        WordMng wordMng = new WordMng();
        Content content = ContentFactory.getInstance()
                .createContent(wordMng.getBasePanel(), "", false);

        toolWindow.getContentManager()
                .addContent(content);
    }
}
