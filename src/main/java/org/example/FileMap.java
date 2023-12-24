package org.example;

import java.io.IOException;
import java.util.*;

public class FileMap {

    // Map structure to store word occurrences in different files along with their positions
    private Map<String, Map<String, List<Integer>>> linkedlist = new HashMap<>();

    // Returns a list of positions of a given word in all documents
    public List<List<Integer>> getWordPositionOnDocumentsList(String word) {
        List<List<Integer>> positionsList = new ArrayList<>();

        // Iterate through each word and its file positions
        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            Map<String, List<Integer>> filePositions = wordEntry.getValue();
            String currentWord = wordEntry.getKey();

            if (currentWord.equals(word)) {
//              // If the current word matches the given word, add its positions to the list
                for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
                    List<Integer> positions = fileEntry.getValue();
                    positionsList.add(positions);
                }
            }
        }

        return positionsList;
    }

    // Returns the positions of a word in a specific document
    public List<String> getDocumentsWithWord(String word) {
        List<String> fileList = new ArrayList<>();

        // Iterate through each word and its associated documents
        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            String currentWord = wordEntry.getKey();

            // If the current word matches the given word, add the documents to the list
            if (currentWord.equals(word)) {
                Map<String, List<Integer>> filePositions = wordEntry.getValue();
                fileList.addAll(filePositions.keySet());
            }
        }
        return fileList;
    }

    // Returns the positions of a word in a specific document
    public List<Integer> getWordPositionsInDocument(String word, String documentName) {

        List<List<Integer>> positionsList = getWordPositionOnDocumentsList(word);
        List<String> documentList = getDocumentsWithWord(word);

        // Find the index of the specified document in the document list
        int documentIndex = documentList.indexOf(documentName);
        if (documentIndex != -1 && documentIndex < positionsList.size()) {
            // Return the positions for the specific document
            return positionsList.get(documentIndex);

        } else {
            // If the document is not found, return an empty list or null based on your preference
            return new ArrayList<>(); // Return empty list if document not found
        }
    }


    // Map to store the occurrence count of each word
    private Map<String, Integer> occurences = new HashMap<>();

    // Returns the total occurrence count of a word
    public int getTotalOccurence(String word) {
        return occurences.getOrDefault(word, 0);
    }

    // Returns a map of words following a given word along with their counts
    public Map<String, Integer> getFollowingWord(String word, WordMap wordMap) {
        Map<String, Integer> followingWordCount = new HashMap<>();

        // Iterate through each word and its file positions
        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            String currentWord = wordEntry.getKey();
            Map<String, List<Integer>> filePositions = wordEntry.getValue();

            // If the current word matches the given word, process its following words
            if (currentWord.equals(word)) {
                for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
                    String fileName = fileEntry.getKey();
                    List<Integer> positions = fileEntry.getValue();

                    // Get the processed text for each file
                    List<String> wordsList = wordMap.getProcessedTextForFile(fileName);

                    for (Integer position : positions) {

                        if (position < wordsList.size()) {
                            String nextWord = wordsList.get(position);

                            // map update
                            followingWordCount.put(nextWord, followingWordCount.getOrDefault(nextWord, 0) + 1);
                        }
                    }
                }
            }
        }

        return followingWordCount;
    }

    // Links a list of positions to a specific word in a specific file
    public void linkList(String word, List<Integer> positions, String fileName) {

        // Initialize the map for the word if not present
        linkedlist.computeIfAbsent(word, k -> new HashMap<>());

        Map<String, List<Integer>> filePositions = linkedlist.get(word);

        // Initialize the list for the file if not present
        filePositions.computeIfAbsent(fileName, k -> new ArrayList<>());

        List<Integer> existingPositions = filePositions.get(fileName);

        // Add new positions to the existing list, avoiding duplicates
        for (Integer pos : positions) {
            if (!existingPositions.contains(pos)) {
                existingPositions.add(pos);
            }
        }

        // Increment the occurrence count for the word
        occurences.put(word, occurences.getOrDefault(word, 0) + 1);
    }

}

//Ressources and usefull links for the project

// https://www.tabnine.com/code/java/methods/java.util.Map/entrySet
// https://stackoverflow.com/questions/62533326/concatenate-a-list-of-hashmap-into-one-on-the-same-otder-they-are-and-without-ov






