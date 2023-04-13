package org.example.QAService;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.example.entity.Qa;
import org.example.util.DocxParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/qa")
public class QaController {
    @Autowired
    private QaService qaService;

    @PostMapping("/ask")
    public String ask(@RequestParam("name") String question) {

        return qaService.findByname(question);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        if (file.isEmpty() || !StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".docx")) {
            response.put("error", "File is empty or not a docx file");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            // 处理上传的docx文件
            // ...
            List<String> keywords = new ArrayList<>();
            keywords.add("问：");
            List<List<XWPFParagraph>> parts = DocxParser.splitDocument(document, keywords);
            List<Qa> qaList = new ArrayList<>();
            for (List<XWPFParagraph> part : parts) {
                qaList.add(DocxParser.StringParseToQa(DocxParser.parseToQa(part)));
            }
            for (Qa qa : qaList) {

                System.out.println(qa.getQuestion());
                System.out.println(qa.getAnswer());
                qaService.saveQa(qa);

            }
            document.close();
            response.put("success", true);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("error", "Failed to process file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

}

