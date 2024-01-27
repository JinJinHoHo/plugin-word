package pe.pjh.ws.adapter.in.ide;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.refactoring.rename.RenameProcessor;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.Notation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 클래스 소스명 제안 IntentionAction
 */
@NonNls
public final class ClassSrcNameSuggIntentionAction implements IntentionAction {

    @Override
    public @IntentionName @NotNull String getText() {
        return UiBundle.message("NameSugg_ClassSrcName_intention_text");
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

        PsiDocComment psiDocComment = getPsiDocComment(editor);
        if (psiDocComment == null) return IntentionPreviewInfo.EMPTY;

        String commentTitle = getCommentName(psiDocComment);
        if (commentTitle == null) return IntentionPreviewInfo.EMPTY;

        PsiClass psiClass = (PsiClass) psiDocComment.getOwner();
        if (psiClass == null) return IntentionPreviewInfo.EMPTY;

        String sourceName = AppService.getInstance()
                .getTermSuggestService()
                .requestSourceName(commentTitle, Notation.Pascal);

        return new IntentionPreviewInfo.CustomDiff(
                JavaFileType.INSTANCE,
                commentTitle,
                genClassPreviewTemplate(psiClass, sourceName)
        );
    }


    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiFile psiFile) {

        PsiElement psiElement = PsiUtilBase.getElementAtCaret(editor);

        if (psiElement == null) return false;

        return psiElement.getParent() instanceof PsiDocComment;

    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {

        PsiDocComment psiDocComment = getPsiDocComment(editor);
        if (psiDocComment == null) return;

        PsiClass psiClass = (PsiClass) psiDocComment.getOwner();
        if (psiClass == null) return;

        String commentTitle = getCommentName(psiDocComment);
        if (commentTitle == null) return;

        String sourceName = AppService.getInstance()
                .getTermSuggestService()
                .requestSourceName(commentTitle, Notation.Pascal);

        ApplicationManager.getApplication()
                .invokeLater(
                        () -> WriteCommandAction.runWriteCommandAction(
                                psiClass.getProject(),
                                () -> new RenameProcessor(
                                        psiClass.getProject(), psiClass, sourceName,
                                        false, false)
                                        .run()
                        )
                );
    }

    @Nullable
    private static PsiDocComment getPsiDocComment(Editor editor) {
        PsiElement psiElement = PsiUtilBase.getElementAtCaret(editor);
        if (psiElement == null) return null;

        PsiElement parent = psiElement.getParent();
        if (!(parent instanceof PsiDocComment psiDocComment)) return null;
        return psiDocComment;
    }

    /**
     * 생성 클래스 미리보기 템플렛
     *
     * @param psiClass
     * @param sourceName
     * @return
     */
    private static String genClassPreviewTemplate(PsiClass psiClass, String sourceName) {

        List<PsiElement> psiElements = new ArrayList<>();
        PsiElement[] psiElement = psiClass.getChildren();
        for (int i = 1; i < psiElement.length; i++) {
            PsiElement pe = psiElement[i];
            if (pe instanceof PsiWhiteSpace) continue;
            if (pe.getText().equals("{")) break;
            psiElements.add(pe);
        }
        return String.format("""
                %s {
                }
                """, psiElements.stream()
                .map(e -> {
                    if (e instanceof PsiIdentifier) return sourceName;
                    return e.getText().trim();
                })
                .collect(Collectors.joining(" ")).replaceAll("[\\s]{2,}", " "));
    }

    /**
     * 반환 코멘트 명칭
     *
     * <p>클래스 코멘트 첫라인을 명칭 으로 처리함.</p>
     *
     * @param docComment
     * @return
     */
    private String getCommentName(@NonNls PsiDocComment docComment) {

        PsiElement[] psiElements = docComment.getDescriptionElements();
        if (psiElements.length < 1) {
            return null;
        }
        String titleLine = psiElements[1].getText().trim();
        if (!titleLine.matches("#.+")) return null;

        return titleLine.substring(1);
    }

/*
    class ClassRenameDialog extends RenameDialog {

        String suggestedName;

        public ClassRenameDialog(@NotNull Project project, @NotNull PsiElement psiElement, @Nullable PsiElement nameSuggestionContext,
                                 Editor editor, String suggestedName) {
            super(project, psiElement, nameSuggestionContext, editor);
            this.suggestedName = suggestedName;
        }

//        public ClassRenameDialog(Project project, PsiElement psiElement, PsiElement nameSuggestionContext, Editor editor) {
//            super(project, psiElement, nameSuggestionContext, editor);
//        }

        @Override
        public String[] getSuggestedNames() {
            return List.of(suggestedName).toArray(String[]::new);
        }
    }*/
}