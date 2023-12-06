package org.example;

import java.util.HashMap;
import java.util.Map;

public class WordHashCodeMap {

    private Map<String, Integer> wordMap;

    public WordHashCodeMap() {
        wordMap = new HashMap<>();
    }

    public void addWord(String word) {
        wordMap.put(word, word.hashCode());
    }

    public Integer getHashCode(String word) {
        return wordMap.get(word);
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    // Optional: A method to print all words with their hash codes
    public void printWordHashCodes() {
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            System.out.println("Word: " + entry.getKey() + " - HashCode: " + entry.getValue());
        }
    }

    // Example usage
    public static void main(String[] args) {
        WordHashCodeMap wordHashCodeMap = new WordHashCodeMap();

        // Adding words to the map
        wordHashCodeMap.addWord("hello");
        wordHashCodeMap.addWord("world");
        wordHashCodeMap.addWord("java");

        // Retrieve and print the hash code for a word
        System.out.println("HashCode for 'hello': " + wordHashCodeMap.getHashCode("hello"));

        // Print all words with their hash codes
        wordHashCodeMap.printWordHashCodes();
    }
}
