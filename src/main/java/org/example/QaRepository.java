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


import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@Repository
@Service
public interface QaRepository extends Neo4jRepository<Qa, Long> {


    @Query("MATCH (p:key_word)-[r:matchfor]->(q:QandA) WHERE p.name in $keywords RETURN q")
    Set<Qa> findByKeywords(List<String> keywords);
}
