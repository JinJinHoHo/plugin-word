package pe.pjh.ws.application.service.dataset;

import com.couchbase.lite.Dictionary;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Result;

public class Topic {

    private String topicName;
    private String topicId;

    public Topic(Result result) {
        Dictionary dictionary = result.getDictionary("topic");

        this.topicId = dictionary.getString(Topic.Property.topicId.name());
        this.topicName = dictionary.getString(Property.topicName.name());
    }

    public Topic(String topicName, String topicId) {
        this.topicName = topicName;
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public MutableDocument getDocument() {
        return new MutableDocument(topicId.toString())
                .setString(Property.topicId.name(), topicId)
                .setString(Property.topicName.name(), topicName);
    }

    public enum Property {
        topicId, topicName
    }
}
