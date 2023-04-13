package org.example.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.xssf.model.Styles;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node(labels = "QandA")
public class Qa {

    @Id
    @GeneratedValue
    private long id;

    private String question;
    private String answer;


    @Relationship(type = "matchfor", direction = Relationship.Direction.INCOMING)
    private List<KeyWord> keyWords;


    public Qa(String question, String answer) {
        this.question = question;
        this.answer = answer;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public boolean addKeyWords(KeyWord keyWord) {
        return this.keyWords.add(keyWord);
    }


    @Override
    public String toString() {
        return "Qa{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}