package org.example;

import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;


public class Query {
    private static StringBuilder outputText = new StringBuilder();

    public static void parseQuery(String filePath, WordMap wordMap, FileMap fileMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("search ")) {
                    String searchQuery = line.substring(7); // 7 is the length of "search "
                    String[] wordsArray = searchQuery.split(" ");

                    // Update wordsArray with findMostSimilarWords
                    List<String> updatedWords = findMostSimilarWords(Arrays.asList(wordsArray), wordMap.getAllWords());
                    System.out.println();
                    System.out.println("TFIDF: " + updatedWords);
                    String documentFound = search(updatedWords, wordMap, fileMap);

                    outputText.append(documentFound).append("\n");

                    // IMPORTANT
                    // Anne, please use the updatedWords to pass to your method.
                } else if (line.contains("the most probable bigram of ")) {
                    int startIndex = line.indexOf("the most probable bigram of ") + "the most probable bigram of ".length();
                    String bigramQuery = line.substring(startIndex);
                    String[] wordsArray = bigramQuery.split(" ");

                    // Update wordsArray with findMostSimilarWords
                    List<String> updatedWords = findMostSimilarWords(Arrays.asList(wordsArray), wordMap.getAllWords());

                    // Give the most probable words comming after another

                    //Write the solution in the file
                    String wordFound = mostProbableBigram(updatedWords, wordMap, fileMap);
                    System.out.println("Bigram of: " + updatedWords + " is " + wordFound);
                    System.out.println();

                    outputText.append(wordFound).append("\n");


                } else {
                    System.out.println(line); // Print the line as is if it doesn't match the conditions
                }
                writeToFile(outputText.toString(), "src/main/java/org/example/outputs/solution.txt");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Helper method to write a string to a file
    private static void writeToFile(String text, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to perform a search operation
    public static String search(List<String> updatedWords, WordMap wordMap, FileMap fileMap) {
        List<String> documentNames = wordMap.getListOfDocumentNames();
        Map<String, Float> documentScores = new HashMap<>();

        for (String documentName : documentNames) {
            documentScores.put(documentName, 0.0f);
        }

        // Iterate through each word in updatedWords
        for (String word : updatedWords) {
            // Find documents containing the word
            List<String> documentsWithWord = fileMap.getDocumentsWithWord(word);

            // Process documentsWithWord as needed
            for (String document : documentsWithWord) {
                // TF:
                List<String> allDocumentWords = wordMap.getProcessedTextForFile(document);
                List<Integer> countWordInDocument = fileMap.getWordPositionsInDocument(word, document);
                float tf = countWordInDocument.size() / (float) allDocumentWords.size();

                // IDF:
                float idf = 1 + (float) Math.log(documentNames.size() / (1.0 + documentsWithWord.size()));

                // TF-IDF:
                float tfidf = tf * idf;

                // Update the document score
                float currentScore = documentScores.get(document);
                documentScores.put(document, currentScore + tfidf);
            }
        }
        // Find the document with the highest score
        String highestScoringDocument = null;
        float maxScore = -1.0f;
        for (Map.Entry<String, Float> entry : documentScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                highestScoringDocument = entry.getKey();
            }
        }

        // Further processing or return the highest scoring document
        System.out.println("Document with highest score: " + highestScoringDocument + ", Score: " + maxScore);

        return highestScoringDocument;
    }

    // IMPORTANT!
    //
    // Anne, please use this method for your part
    // Method to find the most probable bigram in a given text
    public static String mostProbableBigram(List<String> updatedWords, WordMap wordMap, FileMap fileMap) {
        // Map to store the probabilities of each possible next word
        Map<String, Double> probabilities = new HashMap<>();

        // Use the last word in the updatedWords list as the query word
        String queryWord = updatedWords.get(updatedWords.size() - 1);

        // Iterate through all documents
        List<String> documentNames = wordMap.getListOfDocumentNames();
        for (String documentName : documentNames) {
            List<String> wordsInDocument = wordMap.getProcessedTextForFile(documentName);

            // Iterate through all words in the document to find the most probable next word
            for (int i = 0; i < wordsInDocument.size() - 1; i++) {
                String currentWord = wordsInDocument.get(i);
                String nextWord = wordsInDocument.get(i + 1);

                // Check if the current word matches the query word
                if (currentWord.equals(queryWord)) {
                    // Calculate the probability of nextWord given queryWord: P(nextWord | queryWord) = C(queryWord, nextWord) / C(queryWord)
                    int countQueryWordNextWord = fileMap.getBigramCount(queryWord, nextWord, documentName);
                    int countQueryWord = wordMap.getWordCount(queryWord);

                    double probability = (double) countQueryWordNextWord / countQueryWord;

                    // Update the probabilities map
                    probabilities.put(nextWord, probabilities.getOrDefault(nextWord, 0.0) + probability);
                }
            }
        }

        // Find the word with the highest accumulated probability
        String mostProbableWord = findMostProbableWord(probabilities);

        return mostProbableWord;
    }

    private static String findMostProbableWord(Map<String, Double> probabilities) {
        String mostProbableWord = null;
        double maxProbability = 0.0;

        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            if (entry.getValue() > maxProbability) {
                maxProbability = entry.getValue();
                mostProbableWord = entry.getKey();
            }
        }

        // If there are multiple words with the same probability, return the smallest one
        if (mostProbableWord != null) {
            for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
                if (entry.getValue() == maxProbability && entry.getKey().compareTo(mostProbableWord) < 0) {
                    mostProbableWord = entry.getKey();
                }
            }
        }

        return mostProbableWord;
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
