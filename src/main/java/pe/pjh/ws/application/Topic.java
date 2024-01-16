package pe.pjh.ws.application;

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
}
