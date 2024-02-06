package pe.pjh.ws.adapter.out.couchbase;

import com.couchbase.lite.*;
import com.intellij.openapi.diagnostic.Logger;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import pe.pjh.ws.adapter.out.WordRepository;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.exception.DataSourceException;
import pe.pjh.ws.application.service.dataset.Condition;
import pe.pjh.ws.application.service.dataset.Pagination;
import pe.pjh.ws.application.service.dataset.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.couchbase.lite.DataSource.collection;

public class LocalCouchbaseWordRepository extends AbstractCouchbase implements WordRepository {

    private static final Logger log = Logger.getInstance(LocalCouchbaseWordRepository.class);

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
    public Integer countWordByTopic(Database database, Integer topicNo) {

        Query query = QueryBuilder
                .select(SelectResult.expression(Function.count(Expression.string("*"))).as("count"))
                .from(collection(getCollection(database)))
                .where(Expression.property(Word.Property.topicNo.name()).equalTo(Expression.intValue(topicNo)));

        try (ResultSet results = query.execute()) {
            Result result = results.next();
            return result == null ? 0 : result.getInt("count");
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public List<Word> findByTopic(
            @NonNls Database database,
            @NonNls Integer topicNo,
            @Nls Condition condition, @NonNls Pagination pagination) {

        /*
          실행되는 쿼리문.
          SELECT * FROM word
          WHERE
              word.topicNo = $topicNo
              and ANY n IN word.names SATISFIES n like $keyword END
          limit $li offset $offs
         */

        //파라미터 설정.
        Parameters parameters = new Parameters()
                .setInt("topicNo", topicNo)
                .setInt("li", pagination.pageSize())
                .setInt("offs", pagination.getPageSize() * (pagination.getPageNumber() - 1));

        //검색어에 따라 추가된 파라미터
        if (condition != null && StringUtils.isNotBlank(condition.keyword())) {
            parameters.setString("wordTextKeyword", "%" + condition.keyword().toUpperCase() + "%");
            parameters.setString("nameKeyword", "%" + condition.keyword() + "%");
        }

        //기본 조건문
        Expression whereExpression = Expression
                .property(Word.Property.topicNo.name()).equalTo(Expression.parameter("topicNo"));

        //검색어에 따라 추가된 조건문
        if (condition != null && StringUtils.isNotBlank(condition.keyword())) {
            VariableExpression namesVal = ArrayExpression.variable("n");
            whereExpression = whereExpression
                    .and(
                            Expression
                                    .property(Word.Property.wordText.name()).like(Expression.parameter("wordTextKeyword"))
                                    .or(ArrayExpression
                                            .any(namesVal)
                                            .in(Expression.property(Word.Property.names.name()))
                                            .satisfies(namesVal.like(Expression.parameter("nameKeyword")))
                                    )
                    );
        }

        Query builderQuery = QueryBuilder
                .select(SelectResult.all())
                .from(collection(getCollection(database)))
                .where(whereExpression)
                .limit(Expression.parameter("li"), Expression.parameter("offs"));
        builderQuery.setParameters(parameters);

        if (log.isDebugEnabled()) {
            try {
                log.debug(builderQuery.explain());
            } catch (CouchbaseLiteException ignored) {
            }
        }

        try (ResultSet results = builderQuery.execute()) {
            return results.allResults().stream().map(Word::new).toList();
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public List<String> requestSourceName(Database database, Integer topicNo, String[] docWords) {


        Query query = QueryBuilder
                .select(
                        SelectResult.property(Word.Property.wordText.name()),
                        SelectResult.property(Word.Property.names.name())
                )
                .from(collection(getCollection(database)))
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
                );

        try (ResultSet results = query.execute()) {

            Map<String, String> map = new HashMap<>();
            results.allResults()
                    .forEach(result -> {
                        String word = result.getString(Word.Property.wordText.name());
                        Array array = result.getArray(Word.Property.names.name());
                        if (array != null) array.toList()
                                .forEach(o -> map.put((String) o, word));

                    });

            return Stream.of(docWords)
                    .map(s -> map.getOrDefault(s, ""))
                    .collect(Collectors.toList());
        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }


    @Override
    public List<List<String>> requestDocumentName(Database database, Integer topicNo, String[] sourceWords) {

        Query query = QueryBuilder
                .select(
                        SelectResult.property(Word.Property.wordText.name()),
                        SelectResult.property(Word.Property.names.name())
                )
                .from(collection(getCollection(database)))
                .where(
                        Expression.property(Word.Property.topicNo.name()).equalTo(Expression.intValue(topicNo))
                                .and(Expression.property(Word.Property.wordText.name())
                                        .in(Stream.of(sourceWords)
                                                .map(Expression::string)
                                                .toArray(value -> new Expression[sourceWords.length])
                                        )
                                )
                );

        if (log.isDebugEnabled()) {
            try {
                log.debug(query.explain());
            } catch (CouchbaseLiteException ignored) {
            }
        }

        try (ResultSet results = query.execute()) {

            Map<String, List<String>> map = results.allResults().stream()
                    .collect(Collectors.toMap(
                                    dictionary -> dictionary.getString(Word.Property.wordText.name()),
                                    dictionary -> {
                                        Array array = dictionary.getArray(Word.Property.names.name());
                                        return (array == null ? new ArrayList<>() : array.toList()).stream()
                                                .map(o -> (String) o)
                                                .toList();
                                    }
                            )
                    );

            return Stream.of(sourceWords)
                    .map(s -> map.getOrDefault(s, new ArrayList<>()))
                    .collect(Collectors.toList());

        } catch (CouchbaseLiteException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    protected String getCollectionName() {
        return WORD_COLLECTION_NAME;
    }
}
