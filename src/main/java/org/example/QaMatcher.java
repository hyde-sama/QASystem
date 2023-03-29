package org.example;

import org.example.entity.Qa;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QaMatcher {
    private List<Qa> qas;

    public QaMatcher(List<Qa> qas) {
        this.qas = qas;
    }

    public QaMatchResult match(List<String> words, List<String> poses, List<String> entities) {
        for (Qa qa : qas) {
            Pattern pattern = Pattern.compile(qa.getPattern());
            Matcher matcher = pattern.matcher(words.toString());
            if (matcher.find()) {
                QaMatchResult result = new QaMatchResult(qa, matcher);
                return result;
            }
        }
        return null;
    }
}
