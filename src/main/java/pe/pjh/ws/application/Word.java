package pe.pjh.ws.application;

import java.util.List;

/*
 "word": "RAFOS",
    "engl_name": "Reference Amount For One Serving",
    "name": ["1회섭취참고량"],
    "description": "一回攝取參考量.  만 3세 이상 소비계층이 통상적으로 소비하는 식품별 1회 섭취량과 시장조사 결과 등을 바탕으로 설정한 값을 말한다. "
 */
public class Word {

    private Topic topic;
    private String word;
    private String englName;

    private List<String> names;

    private String description;

    public Word(Topic topic, String word, String englName, List<String> names, String description) {
        this.topic = topic;
        this.word = word;
        this.englName = englName;
        this.names = names;
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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
}
