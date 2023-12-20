package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        WordMap wordMap = new WordMap();

        //ADDED
        FileMap fileMap = new FileMap();

        wordMap.processFiles();
        // Optionally print the processed files here or in another method

//        wordMap.printProcessedFiles();

        //ADDED
        wordMap.linkWords(fileMap);
        // Print the concatenated words
        fileMap.printLinkedList();
        List<List<Integer>> wordPositionList = fileMap.getWordPositionOnDocumentsList("with");

        System.out.println(wordPositionList);
    }
}
