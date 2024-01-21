package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.*;
import pe.pjh.ws.adapter.out.WordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.service.dataset.Word;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.couchbase.lite.DataSource.collection;

public class LocalCouchbaseWordRepository extends AbstractCouchbase implements WordRepository {

    public static final String WORD_COLLECTION_NAME = "word";

    public LocalCouchbaseWordRepository(DataSource dataSource) {
        super(dataSource);
    }


    public void createWords(Database database, List<Word> words) throws CouchbaseLiteException {

        Collection collection = getCollection(database);

        for (Word word : words) {
            collection.save(word.getDocument());
        }
    }

    @Override
    public Integer countWordByTopic(Database database, Integer topicNo) throws CouchbaseLiteException {

        Collection collection = getCollection(database);

        ResultSet results = QueryBuilder
                .select(SelectResult.expression(Function.count(Expression.string("*"))).as("count"))
                .from(collection(collection))
                .where(Expression.property(Word.Property.topicNo.name()).equalTo(Expression.intValue(topicNo)))
                .execute();

        Result result = results.next();
        return result.getInt("count");
    }

    @Override
    public List<String> requestSourceName(Database database, Integer topicNo, String[] docWords) throws CouchbaseLiteException {


        Collection collection = getCollection(database);
        ResultSet results = QueryBuilder
                .select(
//                        SelectResult.all(),
                        SelectResult.property(Word.Property.word.name()),
                        SelectResult.property(Word.Property.names.name())
                )
                .from(collection(collection))
                .where(
                        Expression
                                .property(Word.Property.topicNo.name()).equalTo(Expression.intValue(topicNo))
                                .in(Stream.of(docWords)
                                        .map(s -> ArrayFunction.contains(
                                                        Expression.property(Word.Property.names.name()),
                                                        Expression.string(s)
                                                )
                                        )
                                        .toArray(value -> new Expression[docWords.length])
                                )
                )
                .execute();

        Map<String, String> map = new HashMap<>();
        results.allResults().forEach(result -> {
            Dictionary dictionary = result.getDictionary("word");
            String word = dictionary.getString("word");
            dictionary.getArray("names")
                    .toList()
                    .forEach(o -> map.put((String) o, word));
        });

        return Stream.of(docWords)
                .map(s -> map.getOrDefault(s, ""))
                .collect(Collectors.toList());
    }


    @Override
    public List<List> requestDocumentName(Database database, Integer topicNo, String[] sourceWords) throws CouchbaseLiteException {


        Collection collection = getCollection(database);
        ResultSet results = QueryBuilder
                .select(
//                        SelectResult.all(),
                        SelectResult.property(Word.Property.word.name()),
                        SelectResult.property(Word.Property.names.name())
                )
                .from(collection(collection))
                .where(
                        Expression
                                .property(Word.Property.topicNo.name()).equalTo(Expression.intValue(topicNo))
                                .in(Stream.of(sourceWords)
                                        .map(s -> ArrayFunction.contains(
                                                        Expression.property(Word.Property.names.name()),
                                                        Expression.string(s)
                                                )
                                        )
                                        .toArray(value -> new Expression[sourceWords.length])
                                )
                )
                .execute();

        Map<String, List<Object>> map = results.allResults().stream()
                .collect(Collectors.toMap(result -> {
                    Dictionary dictionary = result.getDictionary("word");
                    return dictionary.getString("word");
                }, result -> {
                    Dictionary dictionary = result.getDictionary("word");
                    return dictionary.getArray("names").toList();
                }));

        return Stream.of(sourceWords).map(s -> map.getOrDefault(s, Collections.EMPTY_LIST)).collect(Collectors.toList());
    }

    @Override
    protected String getCollectionName() {
        return WORD_COLLECTION_NAME;
    }
}
