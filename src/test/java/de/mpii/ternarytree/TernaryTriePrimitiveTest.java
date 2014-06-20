package de.mpii.ternarytree;

import org.junit.Test;

public class TernaryTriePrimitiveTest{
    
    @Test
    public void testBasicInsertionAndToString1() {
        CommonTrieTest.testBasicInsertionAndToString1(new TernaryTriePrimitive());
    }

    @Test
    public void testBasicInsertionAndToString2() {
        CommonTrieTest.testBasicInsertionAndToString2(new TernaryTriePrimitive());
    }

    @Test
    public void testGet1() {
        CommonTrieTest.testGet1(new TernaryTriePrimitive());
    }


    @Test
    public void testGet2() {
        CommonTrieTest.testGet2(new TernaryTriePrimitive());
    }
    
    @Test
    public void testRigorous() {
        CommonTrieTest.testRigorous(new TernaryTriePrimitive());
    }
}
