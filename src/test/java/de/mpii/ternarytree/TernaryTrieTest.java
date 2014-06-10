package de.mpii.ternarytree;

import static org.junit.Assert.*;

import org.junit.Test;

public class TernaryTrieTest{
    
    @Test
    public void testBasicInsertionAndToString() {
        TernaryTrie tt = new TernaryTrie();
        tt.insert("barack", 0);
        tt.insert("barack obama", 1);
        tt.insert("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }
}
