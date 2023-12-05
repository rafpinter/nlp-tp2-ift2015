package org.example;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.*;

import java.util.*;

import java.util.List;
import java.util.Arrays;

public class Text {
    public static void main(String[] args) {
        // Sample text for testing
        String sampleText = "Hello, world! This is a test text. It includes multiple sentences, and some punctuation.";

        // Parse the text using the Text class
        List<String> processedText = Text.parseText(sampleText);

        // Print the result
        System.out.println("Original Text: " + sampleText);
        System.out.println("Processed Text: " + processedText);
    }

    // Parses the given text and returns some form of representation
    public static List<String> parseText(String text) {
        // Replace punctuation with a space
        String noPunctuation = replacePunctuation(text, " ");

        // Replace two or more consecutive spaces with a single space
        String singleSpaced = replaceSpaces(noPunctuation);

        // Tokenize the text
        List<String> tokens = tokenize(singleSpaced);

        // Lemmatize the text
        List<String> lemmatizedTokens = lemmatize(tokens);

        // Return the list of lemmatized tokens
        return lemmatizedTokens;
    }

    // Replaces punctuation in the text with specified character or removal
    public static String replacePunctuation(String text, String replacement) {
        return text.replaceAll("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]", replacement);
    }

    // Replaces two or more consecutive spaces in the text with a single space
    public static String replaceSpaces(String text) {
        return text.replaceAll("\\s{2,}", " ");
    }


    // Tokenizes the text into an array/list of words
    public static List<String> tokenize(String text) {
        // Set up the CoreNLP pipeline with the tokenizer
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Create an empty Annotation with the given text
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        // List to store the tokens
        List<String> tokens = new ArrayList<>();

        // Iterate over all tokens and add them to the list
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                tokens.add(token.originalText());
            }
        }

        return tokens;
    }

    // Performs part-of-speech tagging on the text
    public static List<String> pos(String text) {
        // Setting up the CoreNLP pipeline with POS tagging
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Create an empty Annotation with the given text
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        // List to store the tagged words
        List<String> posTags = new ArrayList<>();

        // Iterate over all tokens and add POS tags to the list
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                posTags.add(token.word() + "/" + pos);
            }
        }

        return posTags;
    }

    // Lemmatizes the text
    public static List<String> lemmatize(List<String> tokens) {
        // Setting up the CoreNLP pipeline with Lemmatization
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        List<String> lemmatizedTokens = new ArrayList<>();

        // Create an empty Annotation with the given text
        for (String token : tokens) {
            Annotation document = new Annotation(token);
            pipeline.annotate(document);

            // Iterate over all tokens and add lemmas to the list
            for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
                for (CoreLabel cl : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    String lemma = cl.get(CoreAnnotations.LemmaAnnotation.class);
                    lemmatizedTokens.add(lemma);
                }
            }
        }

        return lemmatizedTokens;
    }
}
