package pe.pjh.ws.application;

import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

public class Topic {

    private String topicName;
    private Integer topicNo;

    public Topic(String topicName, Integer topicNo) {
        this.topicName = topicName;
        this.topicNo = topicNo;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTopicNo() {
        return topicNo;
    }

    public void setTopicNo(Integer topicNo) {
        this.topicNo = topicNo;
    }

    public MutableDocument getDocument() {
        return new MutableDocument(topicNo.toString())
                .setInt("topicNo", topicNo)
                .setString("topicName", topicName);
    }
}
