package org.example.entity;

import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;


@Node(labels = "key_word")
public class KeyWord {

    @Id
    @GeneratedValue
    private long id;

    @Property(name = "name")
    private String name;

    @Relationship(type = "HAS_KEYWORD", direction = Relationship.Direction.OUTGOING)
    private List<Qa> qas = new ArrayList<>();


    public KeyWord(String name) {
        this.name = name;
    }

    public KeyWord(String name, List<Qa> qas) {
        this.name = name;
        this.qas = qas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Qa> getQas() {
        return qas;
    }

    public void setQas(List<Qa> qas) {
        this.qas = qas;
    }

    public boolean addQa(Qa qa) {
        if (qas == null) {
            qas = new ArrayList<>();
        }
        return this.qas.add(qa);
    }


    public String getName() {
        return name;
    }


}
