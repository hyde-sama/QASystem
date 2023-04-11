package org.example;

import com.hankcs.hanlp.HanLP;
import org.example.util.FileToNeo;
import org.example.util.JaccardSimilarity;
import org.example.util.StringSimilarityCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class HanLPTest {

    @Autowired
    private QaRepository qaRepository;

    @Test
    public void Test() {
        List<String> l1 = HanLP.extractKeyword("在企业长期坐班的年轻人应该怎么有效预防关节炎", 100);
        System.out.println(l1.toString());
        List<String> l2 = HanLP.extractKeyword("年轻人如何预防关节炎", 100);
        System.out.println(l2.toString());
        System.out.println(JaccardSimilarity.similarity(l1.toArray(new String[0]), l2.toArray(new String[0])));
        System.out.println(StringSimilarityCalculator.calculate(l1.toArray(new String[0]), l2.toArray(new String[0])));
        System.out.println(StringSimilarityCalculator.coscalculate(l1.toArray(new String[0]), l2.toArray(new String[0])));
    }

    @Test
    public void Test1() throws IOException {
        FileToNeo.ReadFileToNeo("C:\\Users\\Lenovo\\Desktop\\工作簿1.xlsx");
    }

}
