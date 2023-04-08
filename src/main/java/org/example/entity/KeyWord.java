package org.example.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node(labels = "key_word")
public class KeyWord {

    @Id
    @GeneratedValue
    private long id;

    private String name;


    public KeyWord(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
