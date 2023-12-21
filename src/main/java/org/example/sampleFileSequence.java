//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class sampleFileSequence {
//    public static void main(String[] args) {
//        // Create a HashMap with a List key and a List value
//        Map<List<String>, List<String>> fileWordMap = new HashMap<>();
//
//        // The key as a list containing a single element "26.txt"
//        List<String> fileKey = Arrays.asList("26.txt");
//
//        // The value as a list of words
//        List<String> words = Arrays.asList("with", "stunning", "photo", "and", "story",
//                "National", "Geographic", "explorer", "Wade",
//                "Davis", "celebrate", "the", "extraordinary",
//                "diversity", "of", "world", "s", "indigenous",
//                "culture", "which", "be", "disappear", "from",
//                "planet", "at", "a", "alarming", "rate");
//
//        // Put the key-value pair in the map
//        fileWordMap.put(fileKey, words);
//
//        // Optionally print the map
//        for (Map.Entry<List<String>, List<String>> entry : fileWordMap.entrySet()) {
//            System.out.println("File: " + entry.getKey() + " - Words: " + entry.getValue());
//        }
//    }
//}
