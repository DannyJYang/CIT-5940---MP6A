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

//import any classes you will need
import java.util.*;

public class InvertedIndex {

    // Root of the BST
    private BSTNode root;

    // define a private static inner class that represents a node in the BST
    private static class BSTNode{
        // keyWord that is indexed
        String keyWord;
        // set of IDs where the keyWord appears
        Set<Integer> documentIDs;
        // the left node stores keywords less than this node's keyword
        // the right node stores keywords greater than this node's keyword
        BSTNode left, right;

        // constructor to initialize each node
        BSTNode(String keyWord, int docID){
            this.keyWord = keyWord;
            this.documentIDs = new HashSet<>();
            this.documentIDs.add(docID);
        }
    }

    // DO NOT CHANGE THE FOLLOWING SET OF STOP_WORDS
    private static final Set<String> STOP_WORDS = Set.of(
            "i", "me", "my" , "myself" , "we" , "our" , "ours" , "ourselves" , "you" , "your" ,
            "yours" , "yourself" , "yourselves" , "he" , "him" , "his" , "himself" , "she" ,
            "her" , "hers" , "herself" , "it" , "its" , "itself" , "they" , "them" , "their" ,
            "theirs" , "themselves" , "what" , "which" , "who" , "whom" , "this" , "that" ,
            "these" , "those" , "am" , "is" , "are" , "was" , "were" , "be" , "been" , "being" ,
            "have" , "has" , "had" , "having" , "do" , "does" , "did" , "doing" , "a" , "an" ,
            "the" , "and" , "but" , "if" , "or" , "because" , "as" , "until" , "while" ,
            "of" , "at" , "by" , "for" , "with" , "about" , "against" , "between" , "into" , "through" ,
            "during" , "before" , "after" , "above" , "below" , "to" , "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when",
            "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such",
            "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just",
            "don", "should", "now", "said", "announced", "company", "industry", "technology", "system", "application",
            "software", "update", "service"
    );

    /*
     This method adds a document
     @param int docID, String text
     @return no return
     */
    // addDocument
    public void addDocument(int docID, String text) {

        if (text == null || text.isEmpty()) {
            return;
        }

        String[] words = tokenize(text);

        for (String word : words) {
            if (word.isEmpty() || STOP_WORDS.contains(word)) {
                continue;
            }

            root = insert(root, word, docID);
        }

        return;
    }

    private String[] tokenize(String text) {
        if (text == null || text.isBlank()) {
            return new String[0];
        }

        text = text.toLowerCase().replaceAll("[^a-z0-9\\s-]", " ");
        text = text.replaceAll("^-+", "");
        text = text.replaceAll("-+$", "");
        text = text.replaceAll("\\s-+", " ");
        text = text.replaceAll("-+\\s", " ");
        text = text.trim();

        if (text.isEmpty()) {
            return new String[0];
        }

        return text.split("\\s+");
    }

    private BSTNode insert(BSTNode node, String word, int docID) {
        // The correct insertion location was found
        if (node == null) {
            return new BSTNode(word, docID);
        }

        int comparison = word.compareTo(node.keyWord);

        if (comparison < 0) {
            // Word comes before this node
            node.left = insert(node.left, word, docID);
        } else if (comparison > 0) {
            // Word comes after this node
            node.right = insert(node.right, word, docID);
        } else {
            // Word already exists so add the document ID
            node.documentIDs.add(docID);
        }

        return node;
    }


    /*
    This method returns a set of document IDs based on the query
    @param String query
    @return Set<Integer>
    */
    //search
    public Set<Integer> search(String query) {
        return null;
    }


    /*
     This method removes a document based on the docID
     @param int docID
     @return void
     */
    // to remove a document traverse the entire tree and remove the given docID from the node's set
    // remove the document ID
    public void removeDocument(int docID){
        return;
    }

    /*
     This method get the map of inverted index
     can be used for testing purposes
     @param none
     @return Map<String, Set<Integer>>
     */
    // returns the map of the inverted index
    public Map<String, Set<Integer>> getIndex() {
       return null;
    }

    /*
     * TODO: Implement helper methods below
     */





}

