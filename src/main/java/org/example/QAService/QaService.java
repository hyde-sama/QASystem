package org.example.QAService;

import com.hankcs.hanlp.HanLP;
import org.example.QaMatchResult;
import org.example.QaRepository;
import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QaService {


    @Autowired
    private QaRepository qaRepository;

    public List<Qa> findByname(String name) {
        // 预处理问题

        return qaRepository.findByName(name);


    }
}

