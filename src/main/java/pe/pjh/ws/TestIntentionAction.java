package pe.pjh.ws;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class TestIntentionAction implements IntentionAction {

    @Override
    public @IntentionName @NotNull String getText() {
        return null;
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {

    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}