package org.example;

import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.util.List;

@Repository
@Service
public interface QaRepository extends Neo4jRepository<Qa, Long> {


    @Query("MATCH (p:key_word)-[r:matchfor]->(q:QandA) WHERE p.name=$name RETURN q")
    List<Qa> findByName(@Param("name") String name);
}
