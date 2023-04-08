package org.example;

import com.hankcs.hanlp.HanLP;
import org.example.entity.Qa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HanLPTest {

    @Autowired
    private QaRepository qaRepository;

    @Test
    public void Test() {
        List<String> l1 = HanLP.extractKeyword("在企业长期坐班的年轻人如何有效预防关节炎", 20);
        System.out.println(l1.toString());
        List<String> l2 = HanLP.extractKeyword("年轻人如何预防关节炎", 5);
        System.out.println(JaccardSimilarity.similarity(l1.toArray(new String[0]), l2.toArray(new String[0])));
    }

    @Test
    public void Test1() {

    }

}
