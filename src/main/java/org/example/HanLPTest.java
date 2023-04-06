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
        List<Qa> qaList = qaRepository.findByName("关节炎");
        System.out.println(qaList.toString());
    }

}
