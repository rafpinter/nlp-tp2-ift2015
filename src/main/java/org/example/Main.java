package org.example;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // Creates WordMap and FileMap
        WordMap wordMap = new WordMap();
        FileMap fileMap = new FileMap();

        // Creates Query structure
        Query query = new Query();

        // Processing documents
        wordMap.processFiles();
        wordMap.linkWords(fileMap);

        // Processing queries
        query.parseQuery("src/main/java/org/example/queries/1.txt", wordMap, fileMap);
    }
}
