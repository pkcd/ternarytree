package de.mpii.ternarytree;

import static org.junit.Assert.assertEquals;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.util.Random;

public class CommonTrieTest {

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
    
    private static void assertEqualsList(TIntList a, TIntList b) {
        TIntIterator iter = a.iterator();
        while (iter.hasNext()) {
            b.remove(iter.next());
        }
        assertEquals(0, b.size());
    }
}
