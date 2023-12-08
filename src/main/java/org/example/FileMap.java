package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMap {
    public static void main(String[] args) {
        // Create a map with List keys and List of List of Integer values
        Map<List<String>, List<List<Integer>>> map = new HashMap<>();

        // Create lists to be used as keys
        List<String> key1 = new ArrayList<>();
        key1.add("900.txt");
        key1.add("901.txt");

        List<String> key2 = new ArrayList<>();
        key2.add("900.txt");
        key2.add("903.txt");

        // Create lists of lists to be used as values
        List<List<Integer>> value1 = new ArrayList<>();

        List<Integer> value1Sublist1 = new ArrayList<>();
        value1Sublist1.add(1);
        value1Sublist1.add(2);
        value1.add(value1Sublist1);

        List<Integer> value1Sublist2 = new ArrayList<>(); // Second sublist for value1
        value1Sublist2.add(5);
        value1Sublist2.add(6);
        value1.add(value1Sublist2);

        List<List<Integer>> value2 = new ArrayList<>();

        List<Integer> value2Sublist1 = new ArrayList<>();
        value2Sublist1.add(3);
        value2Sublist1.add(4);
        value2.add(value2Sublist1);

        List<Integer> value2Sublist2 = new ArrayList<>(); // Second sublist for value2
        value2Sublist2.add(7);
        value2Sublist2.add(8);
        value2.add(value2Sublist2);

        // Put entries into the map using the lists as keys and list of lists as values
        map.put(key1, value1);
        map.put(key2, value2);

        // Accessing values from the map using the keys
        List<List<Integer>> retrievedValue1 = map.get(key1);
        System.out.println(key1 + ": " + retrievedValue1);

        List<List<Integer>> retrievedValue2 = map.get(key2);
        System.out.println(key2 + ": " + retrievedValue2);
    }

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
    public void linkList(String word, List<Integer> positions, String fileName) {

        if (!linkedlist.containsKey(word)) {

            linkedlist.put(word, new HashMap<>());
        }

        Map<String, List<Integer>> filePositions = linkedlist.get(word);

        if (!filePositions.containsKey(fileName)) {

            filePositions.put(fileName, new ArrayList<>());
        }

        List<Integer> existingPositions = filePositions.get(fileName);

        existingPositions.addAll(positions);
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






