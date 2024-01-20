package pe.pjh.ws.application.service;

import com.google.gson.Gson;
import pe.pjh.ws.application.Topic;
import pe.pjh.ws.application.Word;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BundleDataSetLoder {

    public static List<Word> load(BundleDataSet bundleDataSet, Topic topic) throws URISyntaxException, IOException {

        String data = Files.readString(Path.of(bundleDataSet.getPath().toURI()));

        Gson gson = new Gson();
        ArrayList<Map<String, Object>> stringMap = gson.fromJson(data, ArrayList.class);

        return stringMap.stream()
                .map(stringObjectMap -> new Word(topic, stringObjectMap))
                .toList();

    }
}
