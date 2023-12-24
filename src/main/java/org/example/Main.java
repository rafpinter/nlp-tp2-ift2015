package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        WordMap wordMap = new WordMap();
        Query query = new Query();

        FileMap fileMap = new FileMap();

        wordMap.processFiles();
        // Optionally print the processed files here or in another method

//        wordMap.printProcessedFiles();

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

        //else if (line.contains("the most probable bigram of ")) {
        //                    int startIndex = line.indexOf("the most probable bigram of ") + "the most probable bigram of ".length();
        //                    String bigramQuery = line.substring(startIndex);
        //                    String[] wordsArray = bigramQuery.split(" ");
        //
        //                    // Update wordsArray with findMostSimilarWords
        //                    List<String> updatedWords = findMostSimilarWords(Arrays.asList(wordsArray), wordMap.getAllWords());
        //
        //                    //Pour les mots qui viennent apres updatedWords
        //                    Map<String, Integer> nextWords = new HashMap<>();
        //
        //                    // Occurences of words coming after Updated Words
        //
        //
        //                    // Iterate through each word in the list and get its occurrences
        //                    for (String word : updatedWords) {
        //                        int occurrenceOfWord = fileMap.getTotalOccurrencesForWord(word);
        //                        System.out.println("Occurrences of '" + word + "': " + occurrenceOfWord);
        //
        //                        // Iterate through each occurrence of the word and get the word coming after
        //                        for (String document : fileMap.getDocumentsWithWord(word)) {
        //                            List<Integer> positions = fileMap.getWordPositionsInDocument(word, document);
        //                            List<String> processedText = fileMap.getProcessedTextForFile(document);
        //
        //                            for (int position : positions) {
        //                                // Check if the next position is valid
        //                                if (position + 1 < processedText.size()) {
        //                                    // Get the word coming after
        //                                    String nextWord = processedText.get(position + 1);
        //
        //                                    // Update the occurrencesMap
        //                                    nextWords.put(nextWord, nextWords.getOrDefault(nextWord, 0) + 1);
        //                                }
        //                            }
        //                            // Print the word coming after
        //                            System.out.println("Word coming after '" + word + "' in document '" + document + "': " + nextWords);
        //
        //                        }
        //                    }
        //
        //                    // Most probable word
        //                    System.out.println("Bigram of: " + updatedWords + " is " );
        //                    System.out.println();
        //                   // outputText.append(wordFound).append("\n");System.out.print(Query.mostProbableBigram(Arrays.asList("coffe"),wordMap,fileMap));
    }
}
