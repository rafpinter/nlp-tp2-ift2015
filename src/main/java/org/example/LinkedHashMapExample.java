//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//public class LinkedHashMapExample {
//    public static void main(String[] args) {
//        // Create a LinkedHashMap with List keys and List of List of Integer values
//        Map<List<String>, List<List<Integer>>> linkedHashMap = new LinkedHashMap<>();
//
//        // Create keys (List of Strings)
//        List<String> key1 = new ArrayList<>();
//        key1.add("902.txt");
//        key1.add("FirstKey-2");
//
//        List<String> key2 = new ArrayList<>();
//        key2.add("SecondKey-1");
//        key2.add("SecondKey-2");
//
//        // Create values (List of List of Integers)
//        List<List<Integer>> value1 = new ArrayList<>();
//        List<Integer> value1Sublist1 = new ArrayList<>();
//        value1Sublist1.add(1);
//        value1Sublist1.add(2);
//        List<Integer> value1Sublist2 = new ArrayList<>();
//        value1Sublist2.add(3);
//        value1Sublist2.add(4);
//        value1.add(value1Sublist1);
//        value1.add(value1Sublist2);
//
//        List<List<Integer>> value2 = new ArrayList<>();
//        List<Integer> value2Sublist1 = new ArrayList<>();
//        value2Sublist1.add(5);
//        value2Sublist1.add(6);
//        List<Integer> value2Sublist2 = new ArrayList<>();
//        value2Sublist2.add(7);
//        value2Sublist2.add(8);
//        value2.add(value2Sublist1);
//        value2.add(value2Sublist2);
//
//        // Put keys and values in LinkedHashMap
//        linkedHashMap.put(key1, value1);
//        linkedHashMap.put(key2, value2);
//
//        // Print the LinkedHashMap
//        for (Map.Entry<List<String>, List<List<Integer>>> entry : linkedHashMap.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
//    }
//}
