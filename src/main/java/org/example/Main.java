package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//TP 2 By :
//Rafaela Pinter
//Anne Sophie Rozefort

public class Main {
    public static void main(String[] args) throws IOException {


        WordMap wordMap = new WordMap();
        Query query = new Query();

        FileMap fileMap = new FileMap();


        // Processing documents
        wordMap.processFiles();
        wordMap.linkWords(fileMap);


        query.parseQuery("src/main/java/org/example/queries/query.txt", wordMap, fileMap);
    }


}
