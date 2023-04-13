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


}
