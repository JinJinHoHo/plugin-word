package pe.pjh.ws.application.service;

import com.google.gson.Gson;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import pe.pjh.ws.application.Topic;
import pe.pjh.ws.application.Word;
import pe.pjh.ws.application.port.out.TopicBatchPort;
import pe.pjh.ws.application.port.out.WordBatchPort;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public final class BundleDataSetService {

    public static BundleDataSetService getInstance() {
        return ApplicationManager.getApplication().getService(BundleDataSetService.class);
    }

    public BundleDataSetService() {
    }

    public List<Word> loadDataSet(BundleDataSet bundleDataSet, Topic topic) throws URISyntaxException, IOException {

        String data = Files.readString(Path.of(bundleDataSet.getPath().toURI()));

        Gson gson = new Gson();
        ArrayList<Map<String, Object>> stringMap = gson.fromJson(data, ArrayList.class);

        return stringMap.stream()
                .map(stringObjectMap -> new Word(topic, stringObjectMap))
                .toList();

    }
}
