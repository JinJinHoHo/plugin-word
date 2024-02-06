package pe.pjh.ws.application.service.dataset;

import org.apache.commons.lang3.StringUtils;
import pe.pjh.ws.util.ExecuterReturnParam2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 표기법 유형
 */
public enum Notation {

    Snake(strings -> strings.stream()
            .map(String::toLowerCase)
            .collect(Collectors.joining("_"))),

    Pascal(strings -> strings.stream()
            .map(s -> StringUtils.substring(s, 0, 1).toUpperCase() + StringUtils.substring(s, 1).toLowerCase())
            .collect(Collectors.joining())),

    Camel(strings -> IntStream.range(0, strings.size())
            .mapToObj(operand -> {
                String word = strings.get(operand);
                if (operand == 0) {
                    return word.toLowerCase();
                }
                return StringUtils.substring(word, 0, 1).toUpperCase() + StringUtils.substring(word, 1).toLowerCase();
            })
            .collect(Collectors.joining()));

    final ExecuterReturnParam2<List<String>, String> executerReturnParam2;

    Notation(ExecuterReturnParam2<List<String>, String> executerReturnParam2) {
        this.executerReturnParam2 = executerReturnParam2;
    }

    public String convert(List<String> words) {
        return executerReturnParam2.execute(words);
    }


}
