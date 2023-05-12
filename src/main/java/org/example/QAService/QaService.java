package org.example.QAService;

import cn.hutool.core.lang.Dict;
import com.hankcs.hanlp.HanLP;
import com.jayway.jsonpath.Criteria;
import org.example.KeyWordRepository;
import org.example.entity.KeyWord;
import org.example.util.JaccardSimilarity;
import org.example.QaRepository;
import org.example.entity.Qa;
import org.neo4j.driver.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QaService {


    @Autowired
    private QaRepository qaRepository;

    @Autowired
    private KeyWordRepository keywordRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;


    public Dict findByname(String name) {
        // 预处理问题
        List<String> key_wordlist = HanLP.extractKeyword(name, 100);
        List<Qa> qas = qaRepository.findByKeywords(key_wordlist);
        if (qas.size() == 0) {
            return Dict.create().set("Flag", 2).set("Data", "抱歉，您的问题我们无法识别。");
        }
        List<Double> result = keyWord_Compare(qas, key_wordlist);
        if (result.get(1) == -1.0) {
            StringBuilder guess_question = new StringBuilder();
            guess_question.append("您是否想问以下问题：\n");
            for (int i = 0; i < qas.size(); i++) {
                guess_question.append(String.format("%d、%s\n", i + 1, qas.get(i).getQuestion()));
            }
            return Dict.create().set("Flag", 1).set("Data", guess_question.toString());
        }

        return Dict.create().set("Flag", 0).set("Data", qas.get(result.get(1).intValue()).getAnswer());
    }

    public List<Double> keyWord_Compare(List<Qa> qas, List<String> key_wordlist) {
        List<Double> result = new ArrayList<>();
        result.add(0, 0.0);
        result.add(1, -1.0);
        double similarity = 0.0;
        for (int i = 0; i < qas.size(); i++) {
            List<String> questionKeyWordSet = HanLP.extractKeyword(qas.get(i).getQuestion(), 100);
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

    @Transactional
    public void saveQa(Qa qa) {
        Qa existingQa = qaRepository.findByQuestion(qa.getQuestion());
        if (existingQa != null) {
            // 存在同属性Qa
        } else {
            List<String> keywordstrlist = HanLP.extractKeyword(qa.getQuestion(), 100);
            List<KeyWord> keyWords = new ArrayList<>();
            keywordstrlist.forEach(keyWord -> {
                keyWords.add(new KeyWord(keyWord));
            });
            keyWords.forEach(keyWord -> {
                qa.addKeyWords(keyWord);
                keyWord.addQa(qa);
                neo4jTemplate.save(keyWord);
            });


//            qa.setKeyWords(keyWords);
//            neo4jTemplate.save(qa);
        }
    }


    public Boolean merge() {
        Integer hasDuplicate = qaRepository.mergeQa() + keywordRepository.mergeKeyWord() + qaRepository.mergeRelationShip();
        return hasDuplicate > 0;
    }
}







