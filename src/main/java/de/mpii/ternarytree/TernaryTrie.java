package de.mpii.ternarytree;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.UnsupportedEncodingException;

/**
 * This class implements an in-memory ternary trie data structure. It maintains
 * a set of integers against an ascii string key.
 */
public class TernaryTrie implements Trie{

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
            repr += " , " + values.toString();
            return repr;
        }
    }

    private Node root;
    
    public TernaryTrie() {
        root = null;
    }
    
    /*
    public TIntList get(String key) {
        Node p = root;
        int pos = 0;
        byte[] chars = getBytes(key);
        while (p != null) {
            if (chars[pos] < p.chr) {
                p = p.left;
            } else if(chars[pos] == p.chr) {
                if (pos == chars.length - 1) {
                    break;
                } else {
                    p = p.equal;
                    pos++;
                }
            } else {
                p = p.right;
            }
        }
        if (p != null) {
            return p.values;
        } else {
            return new TIntArrayList();
        }
    }
    */
    
    public void add(String key, int value) {
        byte[] bytes = getBytes(key);
        root = add(root, bytes, 0, value);
    }
    
    private Node add(Node p, byte[] chars, int pos, int value) {
        byte chr = chars[pos];
        if (p == null) {
            p = new Node(chr);
        }
        if (chr < p.chr) {
            p.left = add(p.left, chars, pos, value);
        } else if (chr == p.chr) {
            if (pos < chars.length  - 1) {
                p.equal = add(p.equal, chars, pos + 1, value);
            } else {
                if (p.values == null) {
                    p.values = new TIntArrayList();
                }
                p.values.add(value);
            }
        } else {
            p.right = add(p.right, chars, pos, value);
        }
        return p;
    }
    
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
            repr = toString(p.left, repr, prefix);
            repr = toString(p.equal, repr, prefix + (char)p.chr);
            repr = toString(p.right, repr, prefix);
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

    public int get(String key, int defaultValue) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void put(String key, int value) {
        // TODO Auto-generated method stub
        
    }

    public String getContent() {
        // TODO Auto-generated method stub
        return null;
    }

    public int get(String key) {
        // TODO Auto-generated method stub
        return 0;
    }
}
