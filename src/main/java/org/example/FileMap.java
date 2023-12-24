package org.example;

import java.io.IOException;
import java.util.*;

public class FileMap {

    private Map<String, Map<String, List<Integer>>> linkedlist = new HashMap<>();

    public List<List<Integer>> getWordPositionOnDocumentsList(String word) {
        List<List<Integer>> positionsList = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            Map<String, List<Integer>> filePositions = wordEntry.getValue();
            String leMot = wordEntry.getKey();

            if (leMot.equals(word)){
//                System.out.println(word + " was found at " + filePositions.entrySet());
                for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
                    List<Integer> positions = fileEntry.getValue();
                    positionsList.add(positions);
                }
            }
        }

        return positionsList;
    }

    public List<String> getDocumentsWithWord(String word) {
        List<String> fileList = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            String currentWord = wordEntry.getKey();

            if (currentWord.equals(word)) {
                Map<String, List<Integer>> filePositions = wordEntry.getValue();
//                System.out.println(word + " was found in files: " + filePositions.keySet());

                fileList.addAll(filePositions.keySet());
            }
        }

        return fileList;
    }

    public List<Integer> getWordPositionsInDocument(String word, String documentName) {
        List<List<Integer>> positionsList = getWordPositionOnDocumentsList(word);
        List<String> documentList = getDocumentsWithWord(word);

        int documentIndex = documentList.indexOf(documentName);
        if (documentIndex != -1 && documentIndex < positionsList.size()) {
            // Return the positions for the specific document
            return positionsList.get(documentIndex);
        } else {
            // If the document is not found, return an empty list or null based on your preference
            return new ArrayList<>();
        }
    }


    // FOR THE MOST PROBABLE BIGRAM
    // OCCCURENCE
    private Map<String, Integer> occurences = new HashMap<>();

    public int getTotalOccurence(String word) {
        return occurences.getOrDefault(word, 0);
    }

    public Map<String, Integer> getFollowingWord(String word, WordMap wordMap) {
        Map<String, Integer> motSuivant = new HashMap<>();

        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
            String motVoulu = wordEntry.getKey();
            Map<String, List<Integer>> filePositions = wordEntry.getValue();

            if (motVoulu.equals(word)) {

                for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
                    String fileName = fileEntry.getKey();
                    List<Integer> positions = fileEntry.getValue();

                    List<String> wordsList = wordMap.getProcessedTextForFile(fileName);

                    for (Integer position : positions) {

                        // Check if the position valid
                        if (position < wordsList.size()) {
                            String nextWord = wordsList.get(position);

                            // map update
                            motSuivant.put(nextWord, motSuivant.getOrDefault(nextWord, 0) + 1);
                        }
                    }
                }
            }
        }

        return motSuivant;
    }




//    public void getWordPositionOnDocumentsList(String word){
//        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
//
//            Map<String, List<Integer>> filePositions = wordEntry.getValue();
//            String leMot = wordEntry.getKey();
//
//            if (leMot.equals(word)){
//                System.out.print(leMot + ": [");
//
//                int count = 0;
//
//                for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
//                    //                System.out.print(fileEntry.getKey());
//                    List<Integer> positions = fileEntry.getValue();
//                    System.out.print(positions);
//
//                    // Print comma and space if it's not the last entry
//                    if (++count < filePositions.size()) {
//
//                        System.out.print(", ");
//                    }
//                }
//                System.out.println("]");
//            }
//        }
//    }

//    public List<List<String>> getListOfDocumentsWithWord(String word){
//
//        List<List<Integer>> positionsList = new ArrayList<>();
//
//        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
//        Map<String, List<Integer>> filePositions = wordEntry.getValue();
//        String leMot = wordEntry.getKey();
//
//        if (leMot.equals(word)){
//            for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
//                List<Integer> positions = fileEntry.getValue();
//                positionsList.add(positions);
//            }
//        }
//    }
//
//        return positionsList;
//    }

    public void linkList(String word, List<Integer> positions, String fileName) {

        // If the word is not in the map, initialize it
        linkedlist.computeIfAbsent(word, k -> new HashMap<>());

        // Get the map for the file
        Map<String, List<Integer>> filePositions = linkedlist.get(word);

        // If the file is not in the map, initialize it
        filePositions.computeIfAbsent(fileName, k -> new ArrayList<>());

        // Get the existing positions for the word in the file
        List<Integer> existingPositions = filePositions.get(fileName);

        // Add the new positions, avoiding duplicates
        for (Integer pos : positions) {
            if (!existingPositions.contains(pos)) {
                existingPositions.add(pos);
            }
        }
        occurences.put(word, occurences.getOrDefault(word, 0) + 1);

//        if (!linkedlist.containsKey(word)) {
//
//            linkedlist.put(word, new HashMap<>());
//        }
//
//        Map<String, List<Integer>> filePositions = linkedlist.get(word);
//
//        if (!filePositions.containsKey(fileName)) {
//
//            filePositions.put(fileName, new ArrayList<>());
//        }
//
//        List<Integer> existingPositions = filePositions.get(fileName);
//
//        existingPositions.addAll(positions);
    }

    public Map<String, Map<String, List<Integer>>> getLinkedlist() {

        return linkedlist;
    }


    public void printLinkedList() {

        for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {

            Map<String, List<Integer>> filePositions = wordEntry.getValue();
            String leMot = wordEntry.getKey();

            System.out.print(leMot + ": [");

            int count = 0;

            for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
//                System.out.print(fileEntry.getKey());
                List<Integer> positions = fileEntry.getValue();
                System.out.print(positions);

                // Print comma and space if it's not the last entry
                if (++count < filePositions.size()) {

                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}

//Ressources and usefull links for the project

// https://www.tabnine.com/code/java/methods/java.util.Map/entrySet
// https://stackoverflow.com/questions/62533326/concatenate-a-list-of-hashmap-into-one-on-the-same-otder-they-are-and-without-ov






