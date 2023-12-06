package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class WordMap {
    // HashMap to store file names and their processed content
    public static Map<String, List<String>> processedFiles = new HashMap<>();
    public WordHashCodeMap wordHashCodeMap = new WordHashCodeMap();

    // add wordMap as attribute

    public static void main(String[] args) throws IOException {
        WordMap wordMap = new WordMap();
        wordMap.processFiles();
        // Optionally print the processed files here or in another method
        wordMap.printProcessedFiles();
    }

    public void processFiles() throws IOException {
        File folder = new File("src/main/java/org/example/dataset");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    List<String> processedText = readAndProcessFile(file);
                    processedFiles.put(file.getName(), processedText);
                    // Add file to the list of files for each word in WordHashCodeMap
                    for (String word : processedText) {
                        wordHashCodeMap.addWord(word, file.getName());
                    }
                }
            }
        }
    }

    public List<String> readAndProcessFile(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Process the text and then add each string to WordHashCodeMap
        List<String> processedText = Text.parseText(fileContent.toString());
        for (String word : processedText) {
            wordHashCodeMap.addWord(word, file.getName()); // Pass the file name as well
        }

        return processedText;
    }

    public void printProcessedFiles() {
        for (Map.Entry<String, List<String>> entry : processedFiles.entrySet()) {
            System.out.println("File: " + entry.getKey());
            System.out.println("Processed Content: " + entry.getValue());
            System.out.println();
        }
        // Optionally print all words with their hash codes
        wordHashCodeMap.printWordHashCodes();
    }

    // WordHashCodeMap class
    public class WordHashCodeMap {
        private Map<String, Integer> wordMap;
        private Map<Integer, List<String>> hashCodeToFileMap;

        public WordHashCodeMap() {
            wordMap = new HashMap<>();
            hashCodeToFileMap = new HashMap<>();
        }

        public void addWord(String word, String fileName) {
            Integer hashCode = word.hashCode();
            wordMap.put(word, hashCode);

            // Update hashCodeToFileMap with condition
            hashCodeToFileMap.computeIfAbsent(hashCode, k -> new ArrayList<>());
            if (!hashCodeToFileMap.get(hashCode).contains(fileName)) {
                hashCodeToFileMap.get(hashCode).add(fileName);
            }
        }

        public Integer getHashCode(String word) {
            return wordMap.get(word);
        }

        public Map<String, Integer> getWordMap() {
            return wordMap;
        }

        public void printWordHashCodes() {
            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                System.out.println("Word: " + entry.getKey() + " - HashCode: " + entry.getValue());
            }

            // Optionally print the hashCodeToFileMap
            printHashCodeToFileMap();
        }

        private void printHashCodeToFileMap() {
            for (Map.Entry<Integer, List<String>> entry : hashCodeToFileMap.entrySet()) {
                System.out.println("HashCode: " + entry.getKey() + " - Files: " + entry.getValue());
            }
        }
    }
}