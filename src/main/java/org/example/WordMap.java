package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordMap {
    // HashMap to store file names and their processed content
    private static Map<String, List<String>> processedFiles = new HashMap<>();

    public static void main(String[] args) throws IOException {
        processFiles();
        // Optionally print the processed files here or in another method
        printProcessedFiles();
    }

    public static void processFiles() throws IOException {
        File folder = new File("src/main/java/org/example/dataset");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // Store the result of readAndProcessFile in the HashMap
                    processedFiles.put(file.getName(), readAndProcessFile(file));
                }
            }
        }
    }

    private static List<String> readAndProcessFile(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Return the processed text
        return Text.parseText(fileContent.toString());
    }

    private static void printProcessedFiles() {
        for (Map.Entry<String, List<String>> entry : processedFiles.entrySet()) {
            System.out.println("File: " + entry.getKey());
            System.out.println("Processed Content: " + entry.getValue());
            System.out.println();
        }
    }
}
