package pe.pjh.ws.adapter.in.ide;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class EditorAreaIllustration extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get access to the editor and caret model.
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        LogicalPosition logicalPos = primaryCaret.getLogicalPosition();
        VisualPosition visualPos = primaryCaret.getVisualPosition();

        int caretOffset = primaryCaret.getOffset();

        // Build and display the caret report.
        String report = logicalPos.toString() + "\n" +
                        visualPos.toString() + "\n" +
                        "Offset: " + caretOffset;
        Messages.showInfoMessage(report, "Caret Parameters Inside The Editor");
    }
}