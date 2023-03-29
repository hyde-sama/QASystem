package org.example.QAService;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import org.example.QaMatchResult;
import org.example.QaMatcher;
import org.example.QaRepository;
import org.example.config.DataSourceConfig;
import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QaService {


    @Autowired
    private DataSourceConfig dataSourceConfig;

    public String answer(String question) {
        // 预处理问题
        List<String> words = HanLP.segment(question).stream().map(n -> n.word).collect(Collectors.toList());
        List<String> poses = HanLP.segment(question).stream().map(n -> n.nature).map(Nature::toString).collect(Collectors.toList());
        List<String> entities = HanLP.newSegment().enableNameRecognize(true).seg(question).stream().map(n -> n.word).collect(Collectors.toList());
        QaRepository qaRepository=new QaRepository(dataSourceConfig.dataSource());
        // 匹配问题模板
        List<Qa> qas = qaRepository.findAll();
        QaMatcher matcher = new QaMatcher(qas);
        QaMatchResult result = matcher.match(words, poses, entities);

        // 生成答案
        if (result != null) {
            return result.generateAnswer();
        } else {
            return "对不起，我不知道该怎么回答您的问题。";
        }
    }
}

