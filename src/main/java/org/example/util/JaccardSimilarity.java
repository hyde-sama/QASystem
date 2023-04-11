package org.example.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JaccardSimilarity {

    public static double similarity(String[] str1, String[] str2) {
        Set<String> setA = new HashSet<>(Arrays.asList(str1));
        Set<String> setB = new HashSet<>(Arrays.asList(str2));
        int intersection = 0;
        for (String s : setA) {
            if (setB.contains(s)) {
                intersection++;
            }
        }
        int union = setA.size() + setB.size() - intersection;
        return (double) intersection / union;
    }
}
