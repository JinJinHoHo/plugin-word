package pe.pjh.ws.adapter.in.ide;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;

public class UiBundle extends AbstractBundle {

    private static final UiBundle INSTANCE = new UiBundle();

    private UiBundle() {
        super("messages");
    }

    public static String message(
            @NonNls
            String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
