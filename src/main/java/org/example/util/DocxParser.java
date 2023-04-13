package org.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.example.entity.Qa;


public class DocxParser {

    private static final String IMAGE_DIR = "src/main/java/images/";


    /**
     * 按问答分割文档
     */
    public static List<List<XWPFParagraph>> splitDocument(XWPFDocument doc, List<String> keywords) {
        List<List<XWPFParagraph>> result = new ArrayList<>();
        List<XWPFParagraph> currentPart = new ArrayList<>();
        for (XWPFParagraph para : doc.getParagraphs()) {
            boolean found = false;
            for (String keyword : keywords) {
                if (para.getText().contains(keyword)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                if (!currentPart.isEmpty()) {
                    result.add(currentPart);
                }
                currentPart = new ArrayList<>();
            }
            currentPart.add(para);
        }
        if (!currentPart.isEmpty()) {
            result.add(currentPart);
        }
        return result;
    }

    /**
     * 保留文档样式，获取图片url
     * 返回待封装字符串
     */
    public static String parseToQa(List<XWPFParagraph> xwpfParagraphList) {
        StringBuilder stringBuilder = new StringBuilder();
        int listIndex = 1;

        /* 判断列表 */
        for (XWPFParagraph para : xwpfParagraphList) {
            if (para.getNumID() != null) {
                stringBuilder.append(listIndex + "、 ");
                listIndex++;
            }
            for (XWPFRun run : para.getRuns()
            ) {
                try {
                    /* 判断图片 */
                    if (run.getEmbeddedPictures().size() > 0) {
                        for (XWPFPicture picture : run.getEmbeddedPictures()) {

                            stringBuilder.append("<img url=\"" + saveImage(picture) + "\">");
                        }
                    }
                    /* 判断加粗、字体颜色 */
                    if (run.isBold() && run.toString().length() != 0) {
                        if (run.getColor() != null && run.getColor().length() == 6) {
                            stringBuilder.append("<b>" + "<span style=\"color: " + run.getColor() + "\">" + run + "</span>" + "</b>");
                        } else {
                            stringBuilder.append("<b>" + run + "</b>");
                        }
                    } else if (run.getColor() != null && run.getColor().length() == 6) {
                        stringBuilder.append("<span style=\"color: " + run.getColor() + "\">" + run + "</span>");
                    } else {
                        stringBuilder.append(run.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取图片数据 存储在/images文件夹下 并返回图片url
     */
    public static String saveImage(XWPFPicture picture) throws IOException {
        XWPFPictureData pictureData = picture.getPictureData();
        String extension = FilenameUtils.getExtension(pictureData.getFileName());
        byte[] imageData = pictureData.getData();

        String imageName = UUID.randomUUID().toString() + "." + extension;
        String imagePath = IMAGE_DIR + imageName;

        Path path = Paths.get(imagePath);
        Files.createDirectories(path.getParent());
        FileUtils.writeByteArrayToFile(new File(imagePath), imageData);

        return imagePath;
    }


    public static Qa StringParseToQa(String str) throws Exception {
        if (!str.contains("问：") && !str.contains("答：")) {
            throw new Exception();
        }
        String[] strings = str.split("问：|答：");
        Qa qa = new Qa(strings[1], strings[2]);
        return qa;
    }

    public static void main(String[] args) {
        try {
            String fileName = "src/main/resources/云HIS常见问题解答.docx";
            List<String> keywords = new ArrayList<>();
            keywords.add("问：");
            FileInputStream fis = new FileInputStream(fileName);
            XWPFDocument doc = new XWPFDocument(fis);
            List<List<XWPFParagraph>> parts = splitDocument(doc, keywords);
            List<Qa> qaList = new ArrayList<>();
            for (List<XWPFParagraph> part : parts) {
                qaList.add(StringParseToQa(parseToQa(part)));
            }
            for (Qa qa : qaList) {

                System.out.println(qa.getQuestion());
                System.out.println(qa.getAnswer());
            }
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

