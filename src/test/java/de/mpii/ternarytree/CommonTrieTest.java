package de.mpii.ternarytree;

import static org.junit.Assert.*;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CommonTrieTest {

    private Class<? extends Trie> clazz;
    private Trie t;

    @Parameterized.Parameters(name= "{0}")
    public static Collection<Object[]> classesAndMethods() {
        return Arrays
                .asList(new Object[][] { { TernaryTrie.class },
                        { TernaryTrieImplicit.class },
                        { TernaryTriePrimitive.class } });
    }

    public CommonTrieTest(Class<? extends Trie> clazz) {
        this.clazz = clazz;
    }

    @Before
    public void initialize() {
        try {
            t = this.clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBasicInsertionAndGetContent() {
        t.put("barack", 0);
        t.put("barack obama", 1);
        t.put("barack obama", 2);
        t.put("barricade", 2);
        
        String content = t.getContent();
        TObjectIntMap<String> hm = new TObjectIntHashMap<String>();
        for (String keyValue : content.split("\n")) {
            hm.put(keyValue.split("\t")[0], Integer.valueOf(keyValue.split("\t")[1]));
        }
        assertEquals(0, hm.get("barack"));
        assertEquals(2, hm.get("barack obama"));
        assertEquals(2, hm.get("barricade"));
        // System.out.println(t.getContent());
    }

    @Test
    public void testGet1() {
        t.put("barack", 0);
        t.put("barack obama", 1);
        t.put("barack obama", 2);
        t.put("barricade", 2);

        assertEquals(2, t.get("barack obama", -1));
        assertEquals(0, t.get("barack", -1));
        assertEquals(2, t.get("barricade", -1));
        // System.out.println(tt.toString());
    }

    @Test
    public void testGet2() {
        t.put("barack", 0);
        t.put("obama", 1);
        t.put("obama", 2);
        t.put("zynga", 2);

        assertEquals(0, t.get("barack", -1));
        assertEquals(2, t.get("obama", -1));
        assertEquals(2, t.get("zynga", -1));
        // System.out.println(tt.toString());
    }

    @Test
    public void testGet3() {
        t.put("the red dog", 0);
        t.put("the red", 1);
        t.put("red king", 2);
        t.put("the new kid", 3);
        t.put("a", 4);
        t.put("my name is earl", 5);
        t.put("saving private ryan", 6);
        t.put("saving", 7);
        t.put("academy award", 8);
        t.put("academy award for best actor", 9);

        assertEquals(0, t.get("the red dog", -1));
        assertEquals(1, t.get("the red", -1));
        assertEquals(2, t.get("red king", -1));
        assertEquals(3, t.get("the new kid", -1));
        assertEquals(4, t.get("a", -1));
        assertEquals(5, t.get("my name is earl", -1));
        assertEquals(6, t.get("saving private ryan", -1));
        assertEquals(7, t.get("saving", -1));
        assertEquals(8, t.get("academy award", -1));
        assertEquals(9, t.get("academy award for best actor", -1));

        assertEquals(-1, t.get("academy awar", -1));
        assertEquals(-1, t.get("an", -1));
    }

    @Test
    public void testRigorous() {
        int length = 18;
        // int length = 15;
        int num = 5;
        Random r = new Random();
        for (int j = 0; j < num; j++) {
            String key = "";
            for (int i = 0; i < length; i++) {
                key += (char) (r.nextInt(26) + 'a');
            }
            int existing = t.get(key, -1);
            assertEquals(-1, existing);
            int value = r.nextInt(100);
            t.put(key, value);
            assertEquals(value, t.get(key, -1));
        }
        // System.out.println(tt.toString());
    }

}
