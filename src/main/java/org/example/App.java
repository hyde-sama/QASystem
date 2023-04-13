package org.example;

import com.hankcs.hanlp.HanLP;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.example.QAService.QaService;
import org.example.entity.Qa;
import org.example.util.DocxParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
@EnableNeo4jRepositories
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
