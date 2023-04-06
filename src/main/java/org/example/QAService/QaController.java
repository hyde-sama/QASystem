package org.example.QAService;

import org.example.entity.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qa")
public class QaController {
    @Autowired
    private QaService qaService;

    @PostMapping("/ask")
    public String ask(@RequestParam("name") String question) {
        List<Qa> answer = qaService.findByname(question);
        String qa = answer.toString();
        return qa;
    }
}

