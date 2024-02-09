package pe.pjh.ws.adapter.in.ide.ui;

import pe.pjh.ws.application.service.AppService;
import pe.pjh.ws.application.service.dataset.TopicMngService;

import java.util.*;

/**
 * 단어 관리 모델
 */
public class WordMngModel {

    private final WordMngNewWordTableModel wordMngNewWordTableModel = new WordMngNewWordTableModel();

    private final Map<String, WordMngWordByTopicTableModel> topicTableModelMap = new HashMap<>();

    public WordMngModel() {
//        wordMngWordByTopicTableModels.add(new WordMngWordByTopicTableModel(BundleDataSet.CMN_STN_TRM_6TH));
        initLoad();
    }

    public WordMngNewWordTableModel getWordMngNewWordTableModel() {
        return wordMngNewWordTableModel;
    }

    public Collection<WordMngWordByTopicTableModel> getWordsByTopicTableModels() {
        return topicTableModelMap.values();
    }

    public void initLoad() {
        TopicMngService topicMngService = AppService.getInstance().getTopicMngService();

        topicMngService
                .findByTopic()
                .stream()
                .map(WordMngWordByTopicTableModel::new)
                .forEach(wordMngWordByTopicTableModel -> {
                    topicTableModelMap.put(
                            wordMngWordByTopicTableModel.getTopicId().toString(),
                            wordMngWordByTopicTableModel
                    );
                });
    }
}