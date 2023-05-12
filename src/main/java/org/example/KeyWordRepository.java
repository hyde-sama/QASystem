package org.example;

import org.example.entity.KeyWord;
import org.example.entity.Qa;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface KeyWordRepository extends Neo4jRepository<KeyWord, Long> {

    @Query("MATCH (k:key_word)-[r:HAS_KEYWORD]->(q:QandA) WHERE k.name = $name RETURN DISTINCT k")
    KeyWord findByName(String name);


    @Query("MATCH (n:key_word) WITH toLower(n.name) as name, collect(n) as nodes CALL apoc.refactor.mergeNodes(nodes) yield node RETURN count(*)")
    Integer mergeKeyWord();

    @Query("MERGE (k:key_word {name: $name}) RETURN k")
    KeyWord mergeByName(String name);

}
