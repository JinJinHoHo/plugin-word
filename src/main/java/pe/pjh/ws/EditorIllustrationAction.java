package pe.pjh.ws;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class EditorIllustrationAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(EditorIllustrationAction.class);

    @Override
    public void update(@NotNull AnActionEvent event) {
        // Get required data keys
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        // Set visibility only in the case of
        // existing project editor, and selection
        event.getPresentation().setEnabledAndVisible(project != null
                                                     && editor != null && editor.getSelectionModel().hasSelection());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get all the required data from data keys
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        Document document = editor.getDocument();

        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);

        System.out.println(psiFile.getFileType().getName());


        PsiDirectory psiDirectory = psiFile.getContainingDirectory();


        // Work off of the primary caret to get the selection info
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        String currentText = document.getText(TextRange.create(start, end));
        LOG.info(currentText);
        // MyNotifier.notifyError(project,currentText);
        // Replace the selection with a fixed string.
        // Must do this document change in a write action context.
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, "editor_basics")
        );

        // De-select the text range that was just replaced
        primaryCaret.removeSelection();
    }
}