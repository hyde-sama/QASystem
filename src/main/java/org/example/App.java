package org.example;

import com.hankcs.hanlp.HanLP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Collectors;

@SpringBootApplication
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println(HanLP.newSegment().enableNameRecognize(true).seg(" 郑州996医疗小程序  ，蔺怀儒你来负责，需求就是将923的小程序搬过去，对接阮龙，先沟通下、涉及后台对接，后台人员，你们小组安排。项目上需求、完成时间，前期沟通好，形成记录，维护到项目计划表上。本项目稍后会在研发平台添加").stream().map(n -> n.word).collect(Collectors.toList()).toString());
    }
}
