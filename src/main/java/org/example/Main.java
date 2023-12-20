package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        WordMap wordMap = new WordMap();
        Query query = new Query();

        //ADDED
        FileMap fileMap = new FileMap();

        wordMap.processFiles();
        // Optionally print the processed files here or in another method

//        wordMap.printProcessedFiles();

        //ADDED
        wordMap.linkWords(fileMap);
        // Print the concatenated words
//        fileMap.printLinkedList();
//        List<List<Integer>> wordPositionList = fileMap.getWordPositionOnDocumentsList("with");
//        List<String> documentsPositionList = fileMap.getDocumentsWithWord("with") ;
//
////        System.out.println(wordPositionList);
//        System.out.println("Documentos com with: " + documentsPositionList);
//
//        wordMap.printFileToProcessedTextMap();
//
//        List<String> allWords = wordMap.getAllWords();
//
//        System.out.println(fileMap.getWordPositionsInDocument("with", "26.txt"));

        query.parseQuery("src/main/java/org/example/queries/1.txt", wordMap, fileMap);
    }
}
