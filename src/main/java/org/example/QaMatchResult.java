package org.example;

import org.example.entity.Qa;

import java.util.regex.Matcher;

public class QaMatchResult {
    private Qa qa;
    private Matcher matcher;

    public QaMatchResult(Qa qa, Matcher matcher) {
        this.qa = qa;
        this.matcher = matcher;
    }

    public String generateAnswer() {
        String answer = qa.getAnswer();
        for (int i = 1; i <= matcher.groupCount(); i++) {
            answer = answer.replace("{" + i + "}", matcher.group(i));
        }
        return answer;
    }

}

