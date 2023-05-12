package org.example.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.xssf.model.Styles;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node(labels = "QandA")
public class Qa {

    @Id
    @GeneratedValue
    private long id;

    @Property(name = "question")
    private String question;
    @Property(name = "answer")
    private String answer;


    @Relationship(type = "HAS_KEYWORD", direction = Relationship.Direction.INCOMING)
    private List<KeyWord> keyWords;

    public Qa() {
    }

    public Qa(String question, String answer) {
        this.question = question;
        this.answer = answer;

    }

    public Qa(String question, String answer, List<KeyWord> keyWords) {
        this.question = question;
        this.answer = answer;
        this.keyWords = keyWords;
    }

    public long getId() {
        return id;
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
        if (keyWords == null) {
            keyWords = new ArrayList<>();
        }
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