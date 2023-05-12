package org.example;

import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.PreparedQuery;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;



import java.util.List;


@Repository
public interface QaRepository extends Neo4jRepository<Qa, Long> {


    @Query("MATCH (k:key_word)-[r:HAS_KEYWORD]->(q:QandA) WHERE k.name in $keywords RETURN DISTINCT q")
    List<Qa> findByKeywords(List<String> keywords);

    @Query("MATCH (q:QandA) WHERE q.question = $question RETURN DISTINCT q")
    Qa findByQuestion(String question);

    @Query("MATCH (n:QandA) WITH toLower(n.question) as question, collect(n) as nodes CALL apoc.refactor.mergeNodes(nodes) yield node RETURN count(*)")
    Integer mergeQa();

    @Query("MATCH (a:key_word)-[r:HAS_KEYWORD]-(b:QandA) WITH a, b, collect(r) as rels CALL apoc.refactor.mergeRelationships(rels,{properties:\"combine\"}) YIELD rel RETURN count(rel)")
    Integer mergeRelationShip();

    @Query("MERGE (k:QandA {question: $question}) RETURN k")
    Qa mergeByName(String question);
}
