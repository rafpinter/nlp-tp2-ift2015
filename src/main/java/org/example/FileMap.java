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
}
