package org.example;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.WordMap.processedFiles;

public class WordHashCodeMap {
    private Map<String, Integer> wordMap;
    private Map<Integer, List<String>> hashCodeToFileMap;
    private int size;

    public WordHashCodeMap() {
        wordMap = new HashMap<>();
        hashCodeToFileMap = new HashMap<>();
        size = 0;
    }

    public void addWord(String word, String fileName) {

        if (!wordMap.containsKey(word)) {

            // Assign a unique identifier as the "size of the map + 1"
            int uniqueId = wordMap.size() + 1;
            wordMap.put(word, uniqueId);

            // Initialize the list for this uniqueId in hashCodeToFileMap
            hashCodeToFileMap.put(uniqueId, new ArrayList<>());
        }

        int wordId = wordMap.get(word);
        List<String> fileList = hashCodeToFileMap.get(wordId);

        // Add the file name if it's not already in the list
        if (!fileList.contains(fileName)) {
            fileList.add(fileName);
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
            //System.out.println("Word: " + entry.getKey() + " - Entry position: " + entry.getValue());
        }
        printHashCodeToFileMap();
    }


    private void printHashCodeToFileMap() {
        for (Map.Entry<Integer, List<String>> entry : hashCodeToFileMap.entrySet()) {
            System.out.println("HashCode: " + entry.getKey() + " - Files: " + entry.getValue());
        }
    }

    //This Method retireve the positions of a specific word in each files
    public List<Integer> getPositions(String word, String fileName) {
        List<Integer> positions = new ArrayList<>();
        Integer wordId = getHashCode(word);

        if (wordId != null) {
            List<String> fileList = hashCodeToFileMap.get(wordId);

            if (fileList != null && fileList.contains(fileName)) {
                // Retrieve positions for the specified file
                List<String> processedText = processedFiles.get(fileName);
                for (int i = 0; i < processedText.size(); i++) {
                    if (word.equals(processedText.get(i))) {
                        positions.add(i + 1);
                    }
                }
            }
        }

        return positions;
    }
    public int size() {return size;}

    // Gestion du facteur de charge
    public void resize(int newCapacity) {

        Map<String, Integer> newWordMap = new HashMap<>(newCapacity);
        Map<Integer, List<String>> newHashCode = new HashMap<>(newCapacity);

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {

            int wordId = entry.getValue();
            String word = entry.getKey();

            List<String> fileList = hashCodeToFileMap.get(wordId);

            newHashCode.put(wordId, new ArrayList<>(fileList));
            newWordMap.put(word, wordId);

        }
        hashCodeToFileMap = newHashCode;

        wordMap = newWordMap;

    }
}
