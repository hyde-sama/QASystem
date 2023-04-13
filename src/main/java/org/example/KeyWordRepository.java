package org.example;

import org.example.entity.KeyWord;
import org.example.entity.Qa;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface KeyWordRepository extends Neo4jRepository<KeyWord, Long> {

    @Query("MATCH (k:key_word)-[r:HAS_KEYWORD]->(q:QandA) WHERE k.name = $name RETURN DISTINCT k")
    KeyWord findByName(String name);

}
