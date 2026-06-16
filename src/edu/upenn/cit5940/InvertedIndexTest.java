package edu.upenn.cit5940;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndexTest {
    @BeforeAll
    public static void beforeAll() throws Exception{
        // DO NOT remove this method
        // This method is run once before all the test methods in this class
        // You can use this method to set up any common data needed for the tests
    }

    @BeforeEach
    public void beforeEach() throws Exception{
        // DO NOT remove this method
        // This method is run once before all the test methods in this class
        // You can use this method to set up any common data needed for the tests
    }

    @Test
    public void testAddDocument(){
        // Add your test cases for the addDocument method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
//        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for AddDocument.");
        whenAddDocument_andNullText_thenNothing();
        whenAddDocument_thenAddDocumentToIndex();
        whenAddDocument_withStopWords_thenSkipAndAddValidWords();
        whenAddDocument_andDuplicateWordsWithDifferentIndex_thenReplaceIndex();
    }

    private void whenAddDocument_andNullText_thenNothing() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        InvertedIndex index2 = new InvertedIndex();
        InvertedIndex index3 = new InvertedIndex();

        // ACT
        assertDoesNotThrow(() -> index.addDocument(1, null));
        assertDoesNotThrow(() -> index2.addDocument(1, ""));
        assertDoesNotThrow(() -> index3.addDocument(1, "                   "));

        // ASSERT
        assertTrue(index.getIndex().isEmpty());
        assertTrue(index2.getIndex().isEmpty());
        assertTrue(index3.getIndex().isEmpty());
    }

    private void whenAddDocument_thenAddDocumentToIndex() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();

        // ACT
        //Test document are the only 2 words that pass
        index.addDocument(1, "test document valid words");

        // ASSERT
        assertEquals(4, index.getIndex().size());
        assertTrue(index.getIndex().containsKey("test"));
        assertTrue(index.getIndex().get("test").contains(1));
        assertTrue(index.getIndex().containsKey("document"));
        assertTrue(index.getIndex().get("document").contains(1));
        assertTrue(index.getIndex().containsKey("valid"));
        assertTrue(index.getIndex().get("valid").contains(1));
        assertTrue(index.getIndex().containsKey("words"));
        assertTrue(index.getIndex().get("words").contains(1));
    }

    private void whenAddDocument_withStopWords_thenSkipAndAddValidWords() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();

        // ACT
        //Test document are the only 2 words that pass
        index.addDocument(1, "i me my we our ours test document so than too very");

        // ASSERT
        assertEquals(2, index.getIndex().size());
        assertTrue(index.getIndex().containsKey("test"));
        assertTrue(index.getIndex().get("test").contains(1));
        assertTrue(index.getIndex().containsKey("document"));
        assertTrue(index.getIndex().get("document").contains(1));
        assertFalse(index.getIndex().containsKey("i"));
    }

    private void whenAddDocument_andDuplicateWordsWithDifferentIndex_thenReplaceIndex() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();

        // ACT
        index.addDocument(1, "test document");
        index.addDocument(2, "test document");

        Map<String, Set<Integer>> actual = index.getIndex();

        // ASSERT
        assertEquals(2, actual.size());
        assertEquals(Set.of(1, 2), actual.get("test"));
        assertEquals(Set.of(1, 2), actual.get("document"));
    }

    @Test
    public void testSearch(){
        // Add your test cases for the search method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        whenSearch_andNullQuery_thenReturnEmptySet();
        whenSearch_andMultipleWords_thenReturnDocumentsContainingAllWords();
        whenSearch_andOneWordDoesNotExist_thenReturnEmptySet();
    }

    private void whenSearch_andNullQuery_thenReturnEmptySet() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python");

        // ACT
        Set<Integer> actual = index.search(null);

        // ASSERT
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    private void whenSearch_andMultipleWords_thenReturnDocumentsContainingAllWords() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python database");
        index.addDocument(2, "Java Python");
        index.addDocument(3, "Java database");

        // ACT
        //Should return doc 1 and 2 as both contain both words
        Set<Integer> actual = index.search("JAVA, python!");

        // ASSERT
        assertEquals(Set.of(1, 2), actual);
    }

    private void whenSearch_andOneWordDoesNotExist_thenReturnEmptySet() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python");
        index.addDocument(2, "Java database");

        // ACT
        Set<Integer> actual = index.search("java nonexistent");

        // ASSERT
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testRemoveDocument(){
        // Add your test cases for the removeDocument method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        whenRemoveDocument_andIdDoesNotExist_thenNothing();
        whenRemoveDocument_andIdExists_thenRemoveIdFromAllKeywords();
        whenRemoveDocument_andKeywordHasNoRemainingIds_thenKeepKeywordNode();
    }

    private void whenRemoveDocument_andIdDoesNotExist_thenNothing() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python");
        // ACT
        index.removeDocument(2);

        // ASSERT
        assertEquals(2, index.getIndex().size());
        assertTrue(index.getIndex().containsKey("java"));
        assertTrue(index.getIndex().get("java").contains(1));
        assertTrue(index.getIndex().containsKey("python"));
        assertTrue(index.getIndex().get("python").contains(1));
    }

    private void whenRemoveDocument_andIdExists_thenRemoveIdFromAllKeywords() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python");
        index.addDocument(2, "Java Database");

        // ACT
        index.removeDocument(1);

        // ASSERT
        Map<String, Set<Integer>> actual = index.getIndex();

        //DocID 1 was removed so only docID 2 should be present
        assertEquals(Set.of(2), actual.get("java"));
        assertTrue(actual.get("python").isEmpty());
        assertEquals(Set.of(2), actual.get("database"));
    }

    private void whenRemoveDocument_andKeywordHasNoRemainingIds_thenKeepKeywordNode() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(5, "Unique");

        // ACT
        index.removeDocument(5);

        // ASSERT
        Map<String, Set<Integer>> actual = index.getIndex();

        assertEquals(1, actual.size());
        assertTrue(actual.containsKey("unique"));
        assertTrue(actual.get("unique").isEmpty());
    }

    @Test
    public void testGetIndex(){
        // Add your test cases for the getIndex method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        whenGetIndex_andTreeIsEmpty_thenReturnEmptyMap();
        whenGetIndex_andDocumentsExist_thenReturnCorrectMap();
        whenGetIndex_andKeywordsAreUnsorted_thenReturnAlphabeticalOrder();
    }

    private void whenGetIndex_andTreeIsEmpty_thenReturnEmptyMap() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();

        // ACT
        Map<String, Set<Integer>> actual = index.getIndex();

        // ASSERT
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    private void whenGetIndex_andDocumentsExist_thenReturnCorrectMap() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "Java Python");
        index.addDocument(2, "Java Database");

        // ACT
        Map<String, Set<Integer>> actual = index.getIndex();

        // ASSERT
        assertEquals(3, actual.size());
        assertEquals(Set.of(1, 2), actual.get("java"));
        assertEquals(Set.of(1), actual.get("python"));
        assertEquals(Set.of(2), actual.get("database"));
    }

    private void whenGetIndex_andKeywordsAreUnsorted_thenReturnAlphabeticalOrder() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();
        index.addDocument(1, "zebra apple mango");

        // ACT
        Map<String, Set<Integer>> actual = index.getIndex();
        List<String> actualKeyOrder = new ArrayList<>(actual.keySet());

        // ASSERT
        assertEquals(List.of("apple", "mango", "zebra"), actualKeyOrder);
    }


    /*
    For Extra Credit, is OPTIONAL
     */
    @Test
    public void testSearchWithTFIDF(){
        // Add your test cases for the searchWithTFIDF method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for searchWithTFIDF.");
    }
}
