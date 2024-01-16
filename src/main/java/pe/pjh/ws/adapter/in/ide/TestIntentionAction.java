package pe.pjh.ws.adapter.in.ide;

import com.couchbase.lite.CouchbaseLiteException;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import pe.pjh.ws.application.service.WordDicService;

@NonNls
public final class TestIntentionAction implements IntentionAction {

    @Override
    public @IntentionName @NotNull String getText() {
        return "용어사전 제안 명칭";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "제안 명칭";
    }


    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    public @NotNull IntentionPreviewInfo generatePreview(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        return IntentionAction.super.generatePreview(project, editor, file);
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiFile psiFile) {
        WordDicService service = ServiceManager.getService(WordDicService.class);

        try {
            service.save();
        } catch (CouchbaseLiteException e) {
            throw new RuntimeException(e);
        }
        PsiElement psiElement = PsiUtilBase.getElementAtCaret(editor);

        PsiDocComment psiDocComment;
        PsiElement psiSourceElement = null;

        PsiElement parent = psiElement.getParent();
        if (parent instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) parent;
            psiSourceElement = psiClass;
            psiDocComment = psiClass.getDocComment();
            return true;
        } else if (parent instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) parent;
            psiSourceElement = psiMethod;
            psiDocComment = psiMethod.getDocComment();
            return true;
        } else if (parent instanceof PsiDocComment) {
            psiDocComment = (PsiDocComment) parent;
            psiSourceElement = psiDocComment.getOwner();
            return true;
        }


        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {

    }
}