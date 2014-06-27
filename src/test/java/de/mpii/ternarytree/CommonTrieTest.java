package de.mpii.ternarytree;

import static org.junit.Assert.*;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;

import java.util.Random;

/**
 * JH: The idea of having a unified testing environment for all implementations is nice
 * However, having to put each additonal method in each of the separate test
 * classes is a bit cumbersome. Maybe have a highlevel wrapper that does this? 
 */
public class CommonTrieTest {

    /*
     * JH: do not use toString for testing - toString is meant solely for representation
     *   in human readable form and can easily change, breaking all the tests.
     *   You already have testGet methods, which should be sufficient. With the
     *   way you are testing using toString, you are also dependent on the internal
     *   layout and order of keys - not a good idea.
     */
    public static void testBasicInsertionAndToString1(Trie tt) {
        tt.put("barack", 0);
        tt.put("barack obama", 1);
        tt.put("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }

    public static void testBasicInsertionAndToString2(Trie tt) {
        tt.put("barack", 0);
        tt.put("barack obama", 1);
        tt.put("barack obama", 2);
        tt.put("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1, 2}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }

    public static void testGet1(Trie tt) {
        tt.put("barack", 0);
        tt.put("barack obama", 1);
        tt.put("barack obama", 2);
        tt.put("barricade", 2);
        
        assertEquals(2, tt.get("barack obama", -1));
        assertEquals(0, tt.get("barack", -1));
        assertEquals(2, tt.get("barricade", -1));
        //System.out.println(tt.toString());
    }

    public static void testGet2(Trie tt) {
        tt.put("barack", 0);
        tt.put("obama", 1);
        tt.put("obama", 2);
        tt.put("zynga", 2);
        
        assertEquals(0, tt.get("barack", -1));
        assertEquals(2, tt.get("obama", -1));
        assertEquals(2, tt.get("zynga", -1));
        //System.out.println(tt.toString());
    }
    
    public static void testGet3(Trie tt) {
      tt.put("the red dog", 0);
      tt.put("the red", 1);
      tt.put("red king", 2);
      tt.put("the new kid", 3);
      tt.put("a", 4);
      tt.put("my name is earl", 5);
      tt.put("saving private ryan", 6);
      tt.put("saving", 7);
      tt.put("academy award", 8);
      tt.put("academy award for best actor", 9);
      
      assertEquals(0, tt.get("the red dog", -1));
      assertEquals(1, tt.get("the red", -1));
      assertEquals(2, tt.get("red king", -1));
      assertEquals(3, tt.get("the new kid", -1));
      assertEquals(4, tt.get("a", -1));
      assertEquals(5, tt.get("my name is earl", -1));
      assertEquals(6, tt.get("saving private ryan", -1));
      assertEquals(7, tt.get("saving", -1));
      assertEquals(8, tt.get("academy award", -1));
      assertEquals(9, tt.get("academy award for best actor", -1));
      
      assertEquals(-1, tt.get("academy awar", -1));
      assertEquals(-1, tt.get("an", -1));
  }
    
    public static void testRigorous(Trie tt) {
        int length = 18;
        //int length = 15;
        int num = 5;
        Random r = new Random();
        for (int j = 0; j < num; j++) {
            String key = "";
            for (int i = 0; i < length; i++) {
                key += (char)(r.nextInt(26) + 'a');
            }
            int existing = tt.get(key, -1);
            assertEquals(-1, existing);
            int value = r.nextInt(100);
            tt.put(key, value);
            assertEquals(value, tt.get(key, -1));
        }
        //System.out.println(tt.toString());
    }
    
    /*
     * JH: this modifies the original lists and does not check for order equality,
     * not a robust implementation.
     */
    private static void assertEqualsList(TIntList a, TIntList b) {
        TIntIterator iter = a.iterator();
        while (iter.hasNext()) {
            b.remove(iter.next());
        }
        assertEquals(0, b.size());
    }
}
