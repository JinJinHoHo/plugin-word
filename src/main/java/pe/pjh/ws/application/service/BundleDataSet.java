package pe.pjh.ws.application.service;

import java.net.URL;

public enum BundleDataSet {
    CMN_STN_TRM_6TH(1, "공공데이터 공통표준용어 6차", "/data/data_go_kr_cmn_stn_trm_6th_word.json");

    final Integer topicNo;
    final String name;
    final URL path;

    BundleDataSet(Integer topicNo, String name, String path) {
        this.topicNo = topicNo;
        this.name = name;
        this.path = this.getClass().getResource(path);
    }

    public Integer getTopicNo() {
        return topicNo;
    }

    public String getName() {
        return name;
    }

    public URL getPath() {
        return path;
    }
}
