package de.mpii.ternarytree;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.UnsupportedEncodingException;

/**
 * This class implements an in-memory ternary trie data structure. It maintains
 * a set of integers against an ascii string key.
 * @author pankaj
 */
public class TernaryTrie {

    private class Node {
        private byte chr;
        private Node left, equal, right;
        private TIntList values;
        
        public Node(byte chr) {
            this.chr = chr;
            this.values = null;
            this.left = this.equal = this.right = null;
        }
        
        /**
         * Returns a string representation of a Node. It is a tuple of key
         * character and values i.e "key, { _values_ }"
         */
        @Override
        public String toString() {
            String repr = "";
            repr += (char)chr;
            repr += " , {" + values.toString() + "}";
            return repr;
        }
    }

    private Node root;
    
    public TernaryTrie() {
        root = null;
    }
    
    /**
     * This method inserts a (key, value) pair into the data structure. This
     * does nothing when the given (key, value) pair already exists
     * @param name An ascii string or the key
     * @param entityId An integer or the value
     */
    public void insert(String key, int value) {
        byte[] bytes = null;
        try {
            bytes = key.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        root = insert(root, bytes, 0, value);
    }
    
    private Node insert(Node p, byte[] chars, int pos, int value) {
        byte chr = chars[pos];
        if (p == null) {
            p = new Node(chr);
        }
        if (chr < p.chr) {
            p.left = insert(p.left, chars, pos, value);
        } else if (chr == p.chr) {
            if (pos < chars.length  - 1) {
                p.equal = insert(p.equal, chars, pos + 1, value);
            } else {
                if (p.values == null) {
                    p.values = new TIntArrayList();
                }
                p.values.add(value);
            }
        } else {
            p.right = insert(p.right, chars, pos, value);
        }
        return p;
    }
    
    /**
     * Returns a string representation of the trie. It is a sequence of lines of
     * the form "key, values". The lines are ordered as in depth first traversal
     * where left, equal and right childs are given decreasing priorities.
     */
    @Override
    public String toString() {
        String repr = toString(root, "", "");
        return repr;
    }
    
    private String toString(Node p, String repr, String prefix) {
        if (p != null) {
            if (p.values != null) {
                repr += prefix + p.toString() + "\n";
            }
            repr += toString(p.left, repr, prefix);
            repr += toString(p.equal, repr, prefix + (char)p.chr);
            repr += toString(p.left, repr, prefix);
        }
        return repr;
    }
}
