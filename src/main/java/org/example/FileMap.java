package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMap {
//    public static void main(String[] args) {
//        // Create a map with List keys and List of List of Integer values
//
//    }

    //ADDED
    /**
     *
     * Test to print the resuts but its not stock/doesnt return the result tho
     *
     private Map<String, Map<String, List<Integer>>> linkedlist = new HashMap<>();

    public void linkList(String word, List<Integer> positions, String fileName) {
        // If the word is not in the map, add an entry for it
        if (!linkedlist.containsKey(word)) {
            linkedlist.put(word, new HashMap<>());
        }

        // If the file is not in the map, add an entry for it
        if (!linkedlist.get(word).containsKey(fileName)) {
            linkedlist.get(word).put(fileName, new ArrayList<>());
        }

        // Concatenate positions for the same word and file
        List<Integer> existingPositions = linkedlist.get(word).get(fileName);
        existingPositions.addAll(positions);
        linkedlist.get(word).put(fileName, existingPositions);
    }

     public void printLinkedList() {
     for (Map.Entry<String, Map<String, List<Integer>>> wordEntry : linkedlist.entrySet()) {
     String word = wordEntry.getKey();
     Map<String, List<Integer>> filePositions = wordEntry.getValue();

     System.out.print(word + " : [");

     for (Map.Entry<String, List<Integer>> fileEntry : filePositions.entrySet()) {
     List<Integer> positions = fileEntry.getValue();
     System.out.print(positions);
     System.out.print( ",");
     }
     System.out.println("]");
     }
     }
     **/

    /**

     // This method doesnt associate each word with positions in different files I think
     // Yes probably

     private Map<String, List<List<Integer>>> linkedlist = new HashMap<>();
     public void linkList(String word, List<Integer> positions, String fileName) {
     if (!linkedlist.containsKey(word)) {
     linkedlist.put(word, new ArrayList<>());
     }

     List<Integer> currentPos = new ArrayList<>(positions);
     linkedlist.get(word).add(currentPos);
     }

     public Map<String, List<List<Integer>>> getLinkedlist() {
     return linkedlist;
     }

     public void printLinkedList() {
     for (Map.Entry<String, List<List<Integer>>> wordEntry : linkedlist.entrySet()) {
     String word = wordEntry.getKey();
     List<List<Integer>> filePositionsList = wordEntry.getValue();

     System.out.print(word + ": [");

     int count = 0;
     for (List<Integer> positions : filePositionsList) {
     System.out.print(positions);

     // Print comma and space if it's not the last entry
     if (++count < filePositionsList.size()) {
     System.out.print(", ");
     }
     }

     System.out.println("]");
     }
     }
     **/

    //ADDED

    //This one does associate each word with positions
    // Use a stream ?
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


    // For the most probable bigram
    // HashMap pour stocker les co-occurrences de bigrammes dans chaque document
    private Map<String, Map<String, Integer>> bigramCountMap = new HashMap<>();

    // Méthode pour incrémenter le compteur de co-occurrences d'un bigramme dans un document
    public void incrementBigramCount(String word1, String word2, String document) {
        // Vérifier si le document est déjà dans la carte
        if (!bigramCountMap.containsKey(document)) {
            bigramCountMap.put(document, new HashMap<>());
        }

        // Obtenir la carte de co-occurrences pour le document
        Map<String, Integer> documentBigramCountMap = bigramCountMap.get(document);

        // Obtenir le compteur actuel du bigramme dans le document
        int currentCount = documentBigramCountMap.getOrDefault(word1 + "_" + word2, 0);

        // Incrémenter le compteur
        documentBigramCountMap.put(word1 + "_" + word2, currentCount + 1);
    }

    // Méthode pour obtenir le nombre de co-occurrences d'un bigramme dans un document
    public int getBigramCount(String word1, String word2, String document) {
        // Obtenir la carte de co-occurrences pour le document
        Map<String, Integer> documentBigramCountMap = bigramCountMap.get(document);

        // Retourner le compteur du bigramme, ou 0 si le bigramme n'a pas été trouvé
        return documentBigramCountMap != null ? documentBigramCountMap.getOrDefault(word1 + "_" + word2, 0) : 0;
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






