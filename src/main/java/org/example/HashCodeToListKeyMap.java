package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashCodeToListKeyMap {

    private Map<Integer, List<Integer>> hashCodeToListKeyMap;

    public HashCodeToListKeyMap() {
        hashCodeToListKeyMap = new HashMap<>();
    }

    public void addEntry(String word, List<Integer> listKey, WordHashCodeMap wordHashCodeMap) {
        // Get the hash code for the word
        Integer hashCode = wordHashCodeMap.getHashCode(word);
        if (hashCode != null) {
            // Put the hash code and the list key into the map
            hashCodeToListKeyMap.put(hashCode, listKey);
        } else {
            throw new IllegalArgumentException("Word not found in WordHashCodeMap: " + word);
        }
    }

    public List<Integer> getListKey(Integer hashCode) {
        return hashCodeToListKeyMap.get(hashCode);
    }

    // Example usage
    public static void main(String[] args) {
        // Create WordHashCodeMap and populate it
        WordHashCodeMap wordHashCodeMap = new WordHashCodeMap();
        wordHashCodeMap.addWord("hello");
        wordHashCodeMap.addWord("world");

        // Create HashCodeToListKeyMap
        HashCodeToListKeyMap hashCodeToListKeyMap = new HashCodeToListKeyMap();

        // Create a ListKeyMap example entry
        List<Integer> listKeyForHello = List.of(1, 2, 3);

        // Add entry to HashCodeToListKeyMap
        hashCodeToListKeyMap.addEntry("hello", listKeyForHello, wordHashCodeMap);

        // Retrieve and print the list key using the hash code
        Integer hashCodeForHello = wordHashCodeMap.getHashCode("hello");
        System.out.println("ListKey for hashcode of 'hello': " + hashCodeToListKeyMap.getListKey(hashCodeForHello));
    }
}
