package edu.upenn.cit5940;/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: Danny Yang
 * Penn email: dyang98@seas.upenn.edu
 * Date: 2026-06-14
 */

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


public class Main {

    // helper functions

    // THIS FUNCTION EXTRACTS TITLE
    // IT HAS ALREADY BEEN IMPLEMENTED
    // DO NOT CHANGE THIS FUNCTION
    private static String extractTitle(String combined)
    {
        if (combined == null)
        {
            return "untitled";
        }
        String[] parts = combined.split("\\R", 2);
        String title = parts[0].trim();
        if (title.isEmpty()){
            return "untitled";
        }
        else{
            return title;
        }
    }

    // THIS FUNCTION EXTRACTS THE FIRST SENTENCE
    // IT HAS ALREADY BEEN IMPLEMENTED
    // DO NOT CHANGE THIS FUNCTION
    private static String extractFirstSentence(String combined)
    {
        if (combined == null)
        {
            return "";
        }

        String[] parts = combined.split("\\R", 2);
        String body = (parts.length > 1) ? parts[1].trim() : "";
        if (body.isEmpty())
        {
            return "";
        }

        //split body into sentences that end at a ., !, or ?
        //extract the first sentence with a limit of 200 characters and ...
        String[] sentences = body.split("(?<=[.!?])\\s+");
        String first = sentences[0].replaceAll("\\s+", " ").trim();
        return first.length() > 200 ? first.substring(0, 200) + "..." : first;
    }

    public static void main(String[] args) {

        // create instance of invertedIndex
        InvertedIndex index = new InvertedIndex();

        // create a map to store document ID and combined title + body text
        Map<Integer, String> sampleArticles = new HashMap<>();

        // file path to CSV file
        String csvFilePath = "all_articles_filtered_sample_10.csv";

        int docId = 1;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath))
                .withSkipLines(1)
                .build()) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                // Skip malformed rows that do not contain title and body columns
                if (row.length <= 5) {
                    continue;
                }
                String title = row[4] == null ? "" : row[4].trim();
                String body = row[5] == null ? "" : row[5].trim();
                // Skip rows where both title and body are blank
                if (title.isEmpty() && body.isEmpty()) {
                    continue;
                }
                String combinedText = title + "\n" + body;
                sampleArticles.put(docId, combinedText);
                index.addDocument(docId, combinedText);
                docId++;
            }
        } catch (IOException | CsvValidationException e) {
            return;
        }


        // === MAIN PRINTOUT TO SCREEN ===
        // This section is pre-implemented for clarity.
        // Please do not modify.

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your query to find tech articles related to your interests: ");

        // variable for ranked results
       //InvertedIndexTFIDF tfidf = new InvertedIndexTFIDF();

        // start a loop for User input
        while (true) {
            System.out.println("Enter input (and enter exit to exit the program): ");
            String userInput = scanner.nextLine().trim();

            // if user types exit leave the system
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // variable result for regular search
            Set<Integer> result = index.search(userInput);

            // print the results (not ranked)
            if (result.isEmpty()) {
                System.out.println("No matches found!");
            } else {
                for (int documentID : result) {
                    String combined = sampleArticles.get(documentID);
                    System.out.println("[" +documentID + "] " + extractTitle(combined));
                    String first = extractFirstSentence(combined);
                    if (!first.isEmpty())
                    {
                        System.out.println("    " + first);
                    }
                    //System.out.println("[" + documentID + "] " + sampleArticles.get(documentID));
                    System.out.println();
                }

            }

            System.out.println();

            // print ranked results based on the extra credit
           /*List<Integer> rankedResult = tfidf.searchWithTFIDF(index, userInput, sampleArticles);
            if (rankedResult.isEmpty()){
                System.out.println("No matching documents!");
            } else{
                System.out.println("Ranked Results: ");
                for (int documentID : rankedResult){
                    String combined = sampleArticles.get(documentID);
                    System.out.println("[" + documentID + "] " + extractTitle(combined));
                    String first = extractFirstSentence(combined);
                    if (!first.isEmpty())
                    {
                        System.out.println("    " + first);
                    }
                    //System.out.println("[" + documentID + "] " + sampleArticles.get(documentID));
                    System.out.println();
                }

            } */

        }
        scanner.close();
    }
}