package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.Dictionary;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Result;

public class Topic {

    private String topicName;
    private Integer topicNo;

    public Topic(Result result) {
        Dictionary dictionary = result.getDictionary("topic");

        this.topicNo = dictionary.getInt(Topic.Property.topicNo.name());
        this.topicName = dictionary.getString(Property.topicName.name());
    }

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
                .setInt(Property.topicNo.name(), topicNo)
                .setString(Property.topicName.name(), topicName);
    }

    public enum Property {
        topicNo, topicName
    }
}
