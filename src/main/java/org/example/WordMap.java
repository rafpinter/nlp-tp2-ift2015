package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class WordMap {
    // HashMap to store file names and their processed content
    public static Map<String, List<String>> processedFiles = new HashMap<>();
    public static Map<String, List<String>> fileToProcessedTextMap = new HashMap<>(); // New HashMap
    public WordHashCodeMap wordHashCodeMap = new WordHashCodeMap();
    private static final double loadFactor = 0.75;

    // add wordMap as attribute

    public static void main(String[] args) throws IOException {
        WordMap wordMap = new WordMap();

        FileMap fileMap = new FileMap();


        wordMap.processFiles();

        // wordMap.printProcessedFiles();

        wordMap.linkWords(fileMap);
        // Print the concatenated words
        // fileMap.printLinkedList();

    }

    public void processFiles() throws IOException {
        File folder = new File("src/main/java/org/example/dataset");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    List<String> processedText = readAndProcessFile(file);

                    // Store the processed text in the new map
                    fileToProcessedTextMap.put(file.getName(), processedText);

                    processedFiles.put(file.getName(), processedText);
                    // Add file to the list of files for each word in WordHashCodeMap
                    for (String word : processedText) {
                        wordHashCodeMap.addWord(word, file.getName());
                        resizeWordMap();
                    }
                }
            }
        }
    }

    private void resizeWordMap() {

        int currentSize = wordHashCodeMap.size();
        int currentLoadFactor = currentSize > 0 ? wordHashCodeMap.size() / currentSize : 0;

        if (currentLoadFactor > loadFactor) {
            // redimensionner lacapacité de la Word Map à 2 fois la taille précédente + 1.
            int newSize = currentSize * 2 + 1;
            // System.out.println(newSize);
            wordHashCodeMap.resize(newSize);
        }
    }

    public List<String> readAndProcessFile(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        // Try to read lines
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
            //System.out.println("File: " + entry.getKey());
            //System.out.println("Processed Content: " + entry.getValue());

            // Print word positions for each file
            for (String mot : entry.getValue()) {

                System.out.println("Word: " + mot + "  - Positions:  " + wordHashCodeMap.getPositions(mot, entry.getKey()));
            }

            System.out.println(); //Separate the files
            System.out.println();
            System.out.println();
        }
        //wordHashCodeMap.printWordHashCodes();
    }
    public void printFileToProcessedTextMap() {
        // Aux method to print object
        for (Map.Entry<String, List<String>> entry : fileToProcessedTextMap.entrySet()) {
            String fileName = entry.getKey();
            List<String> processedText = entry.getValue();

            System.out.println("File: " + fileName);
            System.out.println("Processed Text: " + processedText);
            System.out.println(); // For better readability
        }
    }

    public void linkWords(FileMap fileMap) {

        // Links words
        for (Map.Entry<String, List<String>> entry : processedFiles.entrySet()) {

            for (String mot : entry.getValue()) {

                List<Integer> positions = wordHashCodeMap.getPositions(mot, entry.getKey());

                fileMap.linkList(mot, positions, entry.getKey());
            }
        }
    }

    public List<String> getAllWords() {
        return new ArrayList<>(wordHashCodeMap.getWordMap().keySet());
    }
    public List<String> getListOfDocumentNames() {
        return new ArrayList<>(fileToProcessedTextMap.keySet());
    }
    // Method to get processed text for a given file name
    public List<String> getProcessedTextForFile(String fileName) {
        return fileToProcessedTextMap.get(fileName);
    }

}