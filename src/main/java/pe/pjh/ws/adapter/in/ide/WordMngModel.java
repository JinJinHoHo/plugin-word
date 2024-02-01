package pe.pjh.ws.adapter.in.ide;

import pe.pjh.ws.application.service.dataset.BundleDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 단어 관리 모델
 */
public class WordMngModel {

    private final WordMngNewWordTableModel wordMngNewWordTableModel = new WordMngNewWordTableModel();

    private final List<WordMngWordByTopicTableModel> wordMngWordByTopicTableModels = new ArrayList<>();

    public WordMngModel() {
        wordMngWordByTopicTableModels.add(new WordMngWordByTopicTableModel(BundleDataSet.CMN_STN_TRM_6TH));
    }

    public WordMngNewWordTableModel getWordMngNewWordTableModel() {
        return wordMngNewWordTableModel;
    }

    public List<WordMngWordByTopicTableModel> getWordsByTopicTableModels() {
        return wordMngWordByTopicTableModels;
    }
}