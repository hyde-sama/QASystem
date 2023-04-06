package org.example.QAService;

import com.hankcs.hanlp.HanLP;
import org.example.JaccardSimilarity;
import org.example.QaRepository;
import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QaService {


    @Autowired
    private QaRepository qaRepository;

    public Set<Qa> findByname(String name) {
        // 预处理问题
        List<String> key_wordlist = HanLP.extractKeyword(name, 5);
        Set<Qa> qas = qaRepository.findByKeywords(key_wordlist);
//        if (qas.size()==1||qas.size()==0){
//            return qas;
//        }
        return qas;
    }

    public Set<Qa> keyWord_Compare(Set<Qa> qas, List<String> key_wordlist) {

        for (Qa qa : qas
        ) {
            List<String> questionKeyWordSet = HanLP.extractKeyword(qa.getQuestion(), 5);
            double similarity = JaccardSimilarity.
                    similarity(questionKeyWordSet.
                            toArray(new String[questionKeyWordSet.size()]), key_wordlist.toArray(new String[key_wordlist.size()]));
        }

    }



}

