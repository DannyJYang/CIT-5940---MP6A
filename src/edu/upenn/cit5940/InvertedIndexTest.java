package edu.upenn.cit5940;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

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
        assertEquals(null, index.getIndex());
        assertEquals(null, index2.getIndex());
        assertEquals(null, index3.getIndex());
    }

    @Test
    public void whenAddDocument_thenAddDocumentToIndex() {
        // ARRANGE
        InvertedIndex index = new InvertedIndex();

        // ACT
        index.addDocument(1, "This is a test document");

        // ASSERT

    }

    @Test
    public void testSearch(){
        // Add your test cases for the search method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for Search.");
    }

    @Test
    public void testRemoveDocument(){
        // Add your test cases for the removeDocument method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for RemoveDocument.");
    }

    @Test
    public void testGetIndex(){
        // Add your test cases for the getIndex method here
        // Ensure that you have at least 3 distinct and non-trivial test cases
        assertEquals(1, 1, "This is a placeholder test case. Replace with actual test cases for getIndex.");
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
