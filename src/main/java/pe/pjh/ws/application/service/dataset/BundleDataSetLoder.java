package pe.pjh.ws.application.service.dataset;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BundleDataSetLoder {

    public static List<Word> load(BundleDataSet bundleDataSet, Topic topic) throws URISyntaxException, IOException {

        //Todo : 플러그인 실행 환경에서 에러가 발생되 변경. 테스트 환경 에서 확인 필요.
        String data = IOUtils.toString(bundleDataSet.getPath().openStream(), StandardCharsets.UTF_8);
        //String data = Files.readString(Path.of(bundleDataSet.getPath().toURI()));

        Gson gson = new Gson();
        List<Map<String, Object>> stringMap = gson.fromJson(
                data,
                new TypeToken<ArrayList<Map<String, Object>>>(){}.getType()
        );

        return stringMap.stream()
                .map(stringObjectMap -> new Word(topic.getTopicNo(), stringObjectMap))
                .toList();

    }
}
