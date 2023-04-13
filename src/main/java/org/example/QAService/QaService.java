package org.example.QAService;

import com.hankcs.hanlp.HanLP;
import org.example.util.JaccardSimilarity;
import org.example.QaRepository;
import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QaService {


    @Autowired
    private QaRepository qaRepository;

    public String findByname(String name) {
        // 预处理问题
        List<String> key_wordlist = HanLP.extractKeyword(name, 20);
        List<Qa> qas = qaRepository.findByKeywords(key_wordlist);
        if (qas.size() == 0) {
            return "抱歉，您的问题我们无法识别。";
        }
        List<Double> result = keyWord_Compare(qas, key_wordlist);
        if (result.get(1) == -1.0) {
            StringBuilder guess_question = new StringBuilder();
            guess_question.append("您是否想问以下问题：\n");
            for (int i = 0; i < qas.size(); i++) {
                guess_question.append(String.format("%d、%s\n", i + 1, qas.get(i).getQuestion()));
            }
            return guess_question.toString();
        }

        return qas.get(result.get(1).intValue()).getAnswer();
    }

    public List<Double> keyWord_Compare(List<Qa> qas, List<String> key_wordlist) {
        List<Double> result = new ArrayList<>();
        result.add(0, 0.0);
        result.add(1, -1.0);
        double similarity = 0.0;
        for (int i = 0; i < qas.size(); i++) {
            List<String> questionKeyWordSet = HanLP.extractKeyword(qas.get(i).getQuestion(), 5);
            similarity = JaccardSimilarity.
                    similarity(questionKeyWordSet.
                            toArray(new String[questionKeyWordSet.size()]), key_wordlist.toArray(new String[key_wordlist.size()]));
            if (similarity >= 0.5 && similarity > result.get(0)) {
                result.clear();
                result.add(0, similarity);
                result.add(1, (double) i);
            }
        }
        return result;
    }



}

