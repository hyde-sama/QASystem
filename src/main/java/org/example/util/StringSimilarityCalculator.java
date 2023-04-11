package org.example.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Arrays;

public class StringSimilarityCalculator {

    public static double calculate(String[] a, String[] b) {
        String str1 = String.join(" ", a);
        String str2 = String.join(" ", b);
        LevenshteinDistance ld = new LevenshteinDistance();
        int distance = ld.apply(str1, str2);
        return 1.0 - ((double) distance / Math.max(str1.length(), str2.length()));
    }

    public static double coscalculate(String[] a, String[] b) {
        double dotProduct = 0.0;
        double aMagnitude = 0.0;
        double bMagnitude = 0.0;
        for (String str : a) {
            aMagnitude += Math.pow(Arrays.asList(a).stream().filter(s -> s.equals(str)).count(), 2);
            bMagnitude += Math.pow(Arrays.asList(b).stream().filter(s -> s.equals(str)).count(), 2);
            dotProduct += Arrays.asList(b).stream().filter(s -> s.equals(str)).count();
        }
        for (String str : b) {
            if (!Arrays.asList(a).contains(str)) {
                bMagnitude += Math.pow(Arrays.asList(b).stream().filter(s -> s.equals(str)).count(), 2);
            }
        }
        aMagnitude = Math.sqrt(aMagnitude);
        bMagnitude = Math.sqrt(bMagnitude);
        return dotProduct / (aMagnitude * bMagnitude);
    }
}
