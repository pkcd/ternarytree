package de.mpii.ternarytree;

import org.junit.Test;

public class TernaryTrieTest{
    
    @Test
    public void testBasicInsertionAndToString1() {
        CommonTrieTest.testBasicInsertionAndToString1(new TernaryTrie());
    }

    @Test
    public void testBasicInsertionAndToString2() {
        CommonTrieTest.testBasicInsertionAndToString2(new TernaryTrie());
    }

    @Test
    public void testGet1() {
        CommonTrieTest.testGet1(new TernaryTrie());
    }


    @Test
    public void testGet2() {
        CommonTrieTest.testGet2(new TernaryTrie());
    }
    
    @Test
    public void testRigorous() {
        CommonTrieTest.testRigorous(new TernaryTrie());
    }
}
