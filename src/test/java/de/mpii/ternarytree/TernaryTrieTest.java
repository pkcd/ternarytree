package de.mpii.ternarytree;

import static org.junit.Assert.*;

import org.junit.Test;

public class TernaryTrieTest{
    
    @Test
    public void testBasicInsertionAndToString1() {
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

    @Test
    public void testBasicInsertionAndToString2() {
        TernaryTrie tt = new TernaryTrie();
        tt.insert("barack", 0);
        tt.insert("barack obama", 1);
        tt.insert("barack obama", 2);
        tt.insert("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1, 2}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }

    @Test
    public void testGet1() {
        TernaryTrie tt = new TernaryTrie();
        tt.insert("barack", 0);
        tt.insert("barack obama", 1);
        tt.insert("barack obama", 2);
        tt.insert("barricade", 2);
        
        assertEquals("{1, 2}", tt.get("barack obama").toString());
        assertEquals("{0}", tt.get("barack").toString());
        assertEquals("{2}", tt.get("barricade").toString());
        //System.out.println(tt.toString());
    }


    @Test
    public void testGet2() {
        TernaryTrie tt = new TernaryTrie();
        tt.insert("barack", 0);
        tt.insert("obama", 1);
        tt.insert("obama", 2);
        tt.insert("zynga", 2);
        
        assertEquals("{0}", tt.get("barack").toString());
        assertEquals("{1, 2}", tt.get("obama").toString());
        assertEquals("{2}", tt.get("zynga").toString());
        //System.out.println(tt.toString());
    }
}
