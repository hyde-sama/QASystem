package org.example.entity;


import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class MatchFor {

    @Id
    @GeneratedValue
    private long id;

    @TargetNode
    private Qa qa;
}
