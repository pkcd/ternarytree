package de.mpii.ternarytree;

import static org.junit.Assert.*;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

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
        tt.add("barack", 0);
        tt.add("barack obama", 1);
        tt.add("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }

    public static void testBasicInsertionAndToString2(Trie tt) {
        tt.add("barack", 0);
        tt.add("barack obama", 1);
        tt.add("barack obama", 2);
        tt.add("barricade", 2);
        String expected = "barack , {0}\n"
                         +"barack obama , {1, 2}\n"
                         +"barricade , {2}\n";
        assertEquals(expected, tt.toString());
        //System.out.println(tt.toString());
    }

    public static void testGet1(Trie tt) {
        tt.add("barack", 0);
        tt.add("barack obama", 1);
        tt.add("barack obama", 2);
        tt.add("barricade", 2);
        
        assertEquals("{1, 2}", tt.get("barack obama").toString());
        assertEquals("{0}", tt.get("barack").toString());
        assertEquals("{2}", tt.get("barricade").toString());
        //System.out.println(tt.toString());
    }

    public static void testGet2(Trie tt) {
        tt.add("barack", 0);
        tt.add("obama", 1);
        tt.add("obama", 2);
        tt.add("zynga", 2);
        
        assertEquals("{0}", tt.get("barack").toString());
        assertEquals("{1, 2}", tt.get("obama").toString());
        assertEquals("{2}", tt.get("zynga").toString());
        //System.out.println(tt.toString());
    }
    
    public static void testGet3(Trie tt) {
      tt.add("the red dog", 0);
      tt.add("the red", 1);
      tt.add("red king", 2);
      tt.add("the new kid", 3);
      tt.add("a", 4);
      tt.add("my name is earl", 5);
      tt.add("saving private ryan", 6);
      tt.add("saving", 7);
      tt.add("academy award", 8);
      tt.add("academy award for best actor", 9);
      
      assertEquals(0, tt.get("the red dog").get(0));
      assertEquals(1, tt.get("the red").get(0));
      assertEquals(2, tt.get("red king").get(0));
      assertEquals(3, tt.get("the new kid").get(0));
      assertEquals(4, tt.get("a").get(0));
      assertEquals(5, tt.get("my name is earl").get(0));
      assertEquals(6, tt.get("saving private ryan").get(0));
      assertEquals(7, tt.get("saving").get(0));
      assertEquals(8, tt.get("academy award").get(0));
      assertEquals(9, tt.get("academy award for best actor").get(0));
      
      assertTrue(tt.get("academy awar").isEmpty());
      assertTrue(tt.get("an").isEmpty());
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
            TIntList existing = tt.get(key);
            TIntArrayList newList = new TIntArrayList(existing);
            int value = r.nextInt(100);
            newList.add(value);
            tt.add(key, value);
            assertEqualsList(newList , new TIntArrayList(tt.get(key)));
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
