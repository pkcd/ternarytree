package de.mpii.ternarytree;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.UnsupportedEncodingException;

/**
 * This class implements an in-memory ternary trie data structure. It maintains
 * a set of integers against an ascii string key.
 */
public class TernaryTrieImplicit {

    TByteArrayList tree;
    TIntObjectMap<TIntList> lists;
    
    public TernaryTrieImplicit() {
        tree = new TByteArrayList();
        lists = new TIntObjectHashMap<TIntList>();
    }
    
    /**
     * This method returns the list of integer values associated with a key.
     * @param key An ascii string or the key
     * @return The list of integers associated with the key. This is empty if 
     * the key does not exist.
     */
    public TIntList get(String keyString) {
        byte[] chars = getBytes(keyString);
        int node = 0, pos = 0;
        TIntList l = null;
        byte key = tree.getQuick(node);
        while (node < tree.size() && (key = tree.get(node)) != 0) {
            if (chars[pos] < key) {
                node = 3 * node + 1;
            } else if(chars[pos] == key) {
                if (pos == chars.length - 1) {
                    l = lists.get(node);
                    break;
                } else {
                    node = 3 * node + 2;
                    pos++;
                }
            } else {
                node = 3 * node + 3;
            }
        }
        if (l == null) {
            l = new TIntArrayList();
        }
        return l;
    }
    
    /**
     * This method adds a (key, value) pair into the data structure. This
     * does nothing when the given (key, value) pair already exists
     * @param key An ascii string or the key
     * @param value An integer or the value
     */
    public void add(String key, int value) {
        byte[] bytes = getBytes(key);
        add(0, bytes, 0, value);
    }
    
    private void add(int node, byte[] chars, int pos, int value) {
        if (node >= tree.size()) {
            tree.add(new byte[node - tree.size() + 1]);
        }
        byte key = tree.get(node);
        if (key == 0) {
            tree.set(node, (key = chars[pos]));
        }
        if (chars[pos] < key) {
            add(3 * node + 1, chars, pos, value);
        } else if (chars[pos] == key) {
            if (pos < chars.length  - 1) {
                add(3 * node + 2, chars, pos + 1, value);
            } else {
                TIntList list = lists.get(node);
                if (list == null) {
                    list = new TIntArrayList();
                    lists.put(node, list);
                }
                list.add(value);
            }
        } else {
            add(3 * node + 3, chars, pos, value);
        }
    }
    
    /**
     * Returns a string representation of the trie. It is a sequence of lines of
     * the form "key, values". The lines are ordered as in depth first traversal
     * where left, equal and right childs are given decreasing priorities.
     */
    @Override
    public String toString() {
        String repr = toString(0, "", "");
        return repr;
    }
    
    private String toString(int node, String repr, String prefix) {
        byte key = 0;
        if (node < tree.size()) {
            key = tree.get(node);
        }
        if (key != 0) {
            TIntList list = lists.get(node);
            if (list != null) {
                repr += prefix + (char)key + " , " + list.toString() + "\n";
            }
            repr = toString(3 * node + 1, repr, prefix);
            repr = toString(3 * node + 2, repr, prefix + (char)key);
            repr = toString(3 * node + 3, repr, prefix);
        }
        return repr;
    }
    
    private byte[] getBytes(String str) {
        byte[] bytes = null;
        try {
            bytes = str.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
