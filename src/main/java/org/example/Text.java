package org.example;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.util.*;
import java.util.*;
import java.util.List;

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
        String noPunctuationText = replacePunctuation(text, " ");
        StringBuffer word = new StringBuffer();

        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,pos,lemma");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(noPunctuationText);
        // annotate the document
        pipeline.annotate(document);
        // System.out.println(document.tokens());
        for (CoreLabel tok : document.tokens()) {
//            System.out.println(String.format("%s\t%s", tok.word(), tok.lemma()));
            String str = String.valueOf(tok.lemma());
            if (!(str.contains("'s") || str.contains("’s"))) {
                word.append(str).append(" ");
            }
        }
        String str = String.valueOf(word);
        str = str.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();

        // Tokenize the text
        List<String> tokens = tokenize(str);

        return tokens;

    }

    // Replaces punctuation in the text with specified character or removal
    public static String replacePunctuation(String text, String replacement) {
        return text.replaceAll("[^’'a-zA-Z0-9]", replacement).replaceAll("\\s+", " ").trim();
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
}
