package org.example;

import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class Query {

    public static void parseQuery(String filePath, WordMap wordMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("search ")) {
                    String searchQuery = line.substring(7); // 7 is the length of "search "
                    String[] wordsArray = searchQuery.split(" ");

                    // Update wordsArray with findMostSimilarWords
                    List<String> updatedWords = findMostSimilarWords(Arrays.asList(wordsArray), wordMap.getAllWords());
                    System.out.println("TFIDF: " + updatedWords);
                } else if (line.contains("the most probable bigram of ")) {
                    int startIndex = line.indexOf("the most probable bigram of ") + "the most probable bigram of ".length();
                    String bigramQuery = line.substring(startIndex);
                    String[] wordsArray = bigramQuery.split(" ");

                    // Update wordsArray with findMostSimilarWords
                    List<String> updatedWords = findMostSimilarWords(Arrays.asList(wordsArray), wordMap.getAllWords());
                    System.out.println("Bigram of: " + updatedWords);
                } else {
                    System.out.println(line); // Print the line as is if it doesn't match the conditions
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to perform a search operation
//    public static Object search(String query, WordMap wordmap, FileMap filemap, Map<String, List<String>> processedFiles) {
//        // Implementation depends on the data source and search algorithm
//        List<String> allwords = wordmap.wordHashCodeMap.keySet();
//    }

    // Method to find the most probable bigram in a given text
    public static String mostProbableBigram(String query, WordMap wordmap, FileMap filemap, Map<String, List<String>> processedFiles) {
        // Implementation would require analyzing the text to find the most frequent pair of words
        return null;
    }

    public static List<String> findMostSimilarWords(List<String> inputs, List<String> words) {
        List<String> updatedInputs = new ArrayList<>();

        for (String input : inputs) {
            String mostSimilarWord = null;
            int minDistance = Integer.MAX_VALUE;

            for (String word : words) {
                int distance = levenshteinDistance(input, word);
                if (distance < minDistance) {
                    minDistance = distance;
                    mostSimilarWord = word;
                }
            }

            updatedInputs.add(mostSimilarWord != null ? mostSimilarWord : input);
        }

        return updatedInputs;
    }

    // Method to calculate the Levenshtein distance between two strings
    public static int levenshteinDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;

                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[len1][len2];
    }
}
