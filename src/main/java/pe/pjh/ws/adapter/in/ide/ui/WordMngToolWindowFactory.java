package pe.pjh.ws.adapter.in.ide.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public final class WordMngToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        WordMngModel wordMngModel = new WordMngModel();
        RootPanel rootPanel = new RootPanel(wordMngModel);
        Content content = ContentFactory.getInstance()
                .createContent(rootPanel.getBasePanel(), "", false);

        toolWindow.getContentManager()
                .addContent(content);
    }
}
