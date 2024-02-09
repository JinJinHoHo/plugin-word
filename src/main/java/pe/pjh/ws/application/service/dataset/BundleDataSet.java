package pe.pjh.ws.application.service.dataset;

import java.net.URL;

public enum BundleDataSet {
    CMN_STN_TRM_6TH("1", "공공데이터 공통표준용어 6차", "/data/data_go_kr_cmn_stn_trm_6th_word.json");

    final String topicId;
    final String name;
    final URL path;

    BundleDataSet(String topicId, String name, String path) {
        this.topicId = topicId;
        this.name = name;
        this.path = this.getClass().getResource(path);
    }

    public String getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    public URL getPath() {
        return path;
    }

    public Topic getTopic() {
        return new Topic(name, topicId);
    }
}
