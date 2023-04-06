package org.example.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class KeyWord {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Relationship(type = "matchfor")
    private Set<MatchFor> belongsTo = new HashSet<>();

    public KeyWord() {
    }

    public KeyWord(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
