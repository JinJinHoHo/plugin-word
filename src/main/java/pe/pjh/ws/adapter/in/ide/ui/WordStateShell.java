package pe.pjh.ws.adapter.in.ide.ui;

import org.jetbrains.annotations.NonNls;
import pe.pjh.ws.application.service.dataset.Word;

public class WordStateShell {
    Word word;
    WordStatus status;

    Word change = null;


    public WordStateShell(@NonNls Word word) {
        this(word, WordStatus.DEFAULT);
    }

    public WordStateShell(@NonNls Word word, @NonNls WordStatus status) {
        this.word = word;
        this.status = status;
    }

    public void delete() {
        change = null;
        status = WordStatus.DELETE;
    }

    public void change(Word newWord) {
        change = newWord;
        status = WordStatus.CHANGE;
    }

    public void reset() {
        change = null;
        status = WordStatus.DEFAULT;
    }

    public Word getWord() {
        return word;
    }

    public Word getCurrentWord() {
        return change != null ? change : word;
    }

    public WordStatus getStatus() {
        return status;
    }

    public Word getChange() {
        return change;
    }
}
