package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 "word": "RAFOS",
    "engl_name": "Reference Amount For One Serving",
    "name": ["1회섭취참고량"],
    "description": "一回攝取參考量.  만 3세 이상 소비계층이 통상적으로 소비하는 식품별 1회 섭취량과 시장조사 결과 등을 바탕으로 설정한 값을 말한다. "
 */
public class Word {

    private String topicId;
    private String word;
    private String englName;

    private List<String> names;

    private String description;


    public Word(Result result) {
        Dictionary dictionary = result.getDictionary("word");

        this.topicId = dictionary.getString(Property.topicId.name());
        this.word = dictionary.getString(Property.wordText.name());
        this.englName = dictionary.getString(Property.englName.name());
        Array array = dictionary.getArray(Word.Property.names.name());
        if (array != null) {
            this.names = array.toList().stream().map(Object::toString).collect(Collectors.toList());
        } else {
            this.names = new ArrayList<>();
        }

        this.description = dictionary.getString(Property.description.name());
    }

    public Word(String topicId, Map<String, Object> map) {
        this.topicId = topicId;
        this.word = (String) map.get("word");
        this.englName = (String) map.get("engl_name");
        this.names = (List<String>) map.get("name");
        this.description = (String) map.get("description");

        //번틀 데이터에 synonym 있어 임시용으로 만듬.
        String synonym = (String) map.get("synonym");
        if (StringUtils.isNotBlank(synonym)) {
            this.names.addAll(Stream.of(synonym.split(",")).map(String::trim).toList());
        }

    }

    public Word(String topicId, String word, String englName, List<String> names, String description) {
        this.topicId = topicId;
        this.word = word;
        this.englName = englName;
        this.names = names;
        this.description = description;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getEnglName() {
        return englName;
    }

    public void setEnglName(String englName) {
        this.englName = englName;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MutableDocument getDocument() {
        return new MutableDocument("%d_%s".formatted(topicId, word))
                .setString(Property.topicId.name(), topicId).setString(Property.wordText.name(), word).setString(Property.englName.name(), englName).setArray(Property.names.name(), new MutableArray(names.stream().map(s -> (Object) s).toList())).setString(Property.description.name(), description);

    }

    @Override
    public String toString() {
        return "Word{topic=%s, word='%s', englName='%s', names=%s, description='%s'}".formatted(topicId, word, englName, names, description);
    }

    public enum Property {
        topicId, wordText, englName, names, description
    }
}
