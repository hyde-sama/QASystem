package org.example;

import java.util.HashSet;
import java.util.Set;

public class JaccardSimilarity {

    public static double similarity(String[] str1, String[] str2) {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        for (String str : str1) {
            set1.add(str);
        }
        for (String str : str2) {
            set2.add(str);
        }
        Set<String> unionSet = new HashSet<>(set1);
        unionSet.addAll(set2); // 求并集
        Set<String> intersectionSet = new HashSet<>(set1);
        intersectionSet.retainAll(set2); // 求交集
        return (double) intersectionSet.size() / unionSet.size();
    }
}
