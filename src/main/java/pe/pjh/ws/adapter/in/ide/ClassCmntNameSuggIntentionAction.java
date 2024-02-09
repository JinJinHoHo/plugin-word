package pe.pjh.ws.adapter.in.ide;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pe.pjh.ws.adapter.in.ide.ui.UiBundle;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.setting.SettingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 클래스 주석명 제안 인텐션액션
 */
@NonNls
public final class ClassCmntNameSuggIntentionAction implements IntentionAction {


    public static final int LINE_NUMBER_CLASS_TITLE = 1;
    @Override
    public @IntentionName @NotNull String getText() {
        return UiBundle.message("NameSugg_ClassCmntName_intention_text");
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return UiBundle.message("NameSugg_intention_familyname");
    }


    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    public @NotNull IntentionPreviewInfo generatePreview(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {

        PsiClass psiClass = getPsiClass(editor);
        if (psiClass == null) return IntentionPreviewInfo.EMPTY;

        String className = psiClass.getName();
        if (className == null) return IntentionPreviewInfo.EMPTY;

        String documentName = AppService.getInstance()
                .getTermSuggestService()
                .requestDocumentName(className);


        return new IntentionPreviewInfo.CustomDiff(JavaFileType.INSTANCE, className,
                getDefaultCommentFormat(documentName));
    }


    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiFile psiFile) {

        PsiElement psiElement = PsiUtilBase.getElementAtCaret(editor);

        if (psiElement == null) return false;

        if (!(psiElement.getParent() instanceof PsiClass psiClass)) {
            return false;
        }

        PsiDocComment docComment = psiClass.getDocComment();
        if (docComment == null) return true;

        PsiElement[] psiElements = docComment.getDescriptionElements();
        if (psiElements.length < 1) {
            return false;
        }
        return psiElements[1].getText().trim().indexOf("#") == 0;

    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {

        PsiClass psiClass = getPsiClass(editor);
        if (psiClass == null) return;

        String className = psiClass.getName();

        String documentName = AppService.getInstance()
                .getTermSuggestService()
                .requestDocumentName(className);

        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);

        PsiDocComment docComment = psiClass.getDocComment();
        if (docComment == null) {
            //설정된 주석이 없으면 신규 클래스 주석 추가.
            WriteAction.run(() -> psiClass.addBefore(
                    factory.createDocCommentFromText(getDefaultCommentFormat(documentName)),
                    psiClass.getFirstChild())
            );
            return;
        }

        //기존 클래스 주석 분리.
        List<String> lines = new ArrayList<>(Arrays.asList(docComment.getText().split("\n")));

        //클래스 명 수정.
        if (LINE_NUMBER_CLASS_TITLE < lines.size()) lines.set(
                LINE_NUMBER_CLASS_TITLE,
                " * %s%s".formatted(SettingService.NAME_SUGG_PRE_FIX, documentName)
        );

        //소스 반영.
        PsiDocComment newDoc = factory.createDocCommentFromText(String.join("\n", lines));
        WriteAction.run(() -> docComment.replace(newDoc));
    }

    @Nullable
    private static PsiClass getPsiClass(Editor editor) {
        PsiElement psiElement = PsiUtilBase.getElementAtCaret(editor);
        if (psiElement == null) return null;

        PsiElement parent = psiElement.getParent();
        if (!(parent instanceof PsiClass psiClass)) return null;
        return psiClass;
    }

    private static String getDefaultCommentFormat(String documentName) {
        return String.format("""
                /**
                 * %s%s
                 */
                """, SettingService.NAME_SUGG_PRE_FIX, documentName);
    }
}