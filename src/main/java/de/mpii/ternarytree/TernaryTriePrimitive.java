package de.mpii.ternarytree;

import java.io.UnsupportedEncodingException;

import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;

/**
 * JH: Some general comments
 * - please add doc-comments to public methods
 * - please add more comments to parts of the code, e.g. the more higher-up
 *   parts of the while/if constructs to get a quick picture of what is being done.
 *   Even better, follow Diego's suggestion and add all the primitive methods.
 */
public class TernaryTriePrimitive implements Trie{
    // JH: Why not use a char list? This way you are independent of the encoding.
    TByteList labels;
    TIntList nodes;
    // JH: Seeing from the unit tests, you allow the same string to be inserted
    // with the same value. I don't think this is necessary, a regular Map-style
    // single key-value should be fine. This way, you can store the single pointer
    // in nodes and do not need values.
    int root;
    
    public TernaryTriePrimitive() {
        labels = new TByteArrayList();
        nodes = new TIntArrayList();
        root = -1;
    }

    public int get(String keyString, int defaultValue) {
        int node = root;
        int pos = 0;
        byte[] chars = getBytes(keyString);
        while (node != -1) {
            if (chars[pos] < labels.get(node/4)) {
                node = nodes.get(node);
            } else if(chars[pos] == labels.get(node/4)) {
                if (pos == chars.length - 1) {
                    break;
                } else {
                    node = nodes.get(node + 1);
                    pos++;
                }
            } else {
                node = nodes.get(node + 2);
            }
        }
        if (node == -1) {
            return defaultValue;
        } else {
            return nodes.get(node + 3);
        }
    }
    
    public void put(String key, int value) {
        byte[] bytes = getBytes(key);
        root = put(root, bytes, 0, value);
    }
    
    private int put(int node, byte[] chars, int pos, int value) {
        byte chr = chars[pos];
        if (node == -1) {
            node = nodes.size();
            nodes.add(new int[]{-1, -1, -1, -1});
            labels.add(chr);
        }
        if (chr < labels.get(node/4)) {
            nodes.set(node, put(nodes.get(node), chars, pos, value));
        } else if (chr == labels.get(node/4)) {
            if (pos < chars.length  - 1) {
                nodes.set(node + 1, put(nodes.get(node + 1), chars, pos + 1, value));
            } else {
                nodes.set(node + 3, value);
            }
        } else {
             nodes.set(node + 2, put(nodes.get(node + 2), chars, pos, value));
        }
        return node;
    }
    
    @Override
    public String toString() {
        // JH: It's not a good idea to have the whole trie represented in the
        // toString method - this is also used in the debugger and should be concise,
        // e.g.: TT (X nodes).
        String repr = toString(root, "", "");
        return repr;
    }
    
    private String toString(int node, String repr, String prefix) {
        if (node != -1) {
            if (nodes.get(node + 3) != -1) {
                repr += prefix + (char)labels.get(node/4) + " , " + nodes.get(node + 3) + "\n";
            }
            repr = toString(nodes.get(node), repr, prefix);
            repr = toString(nodes.get(node + 1), repr, prefix + (char)labels.get(node/4));
            repr = toString(nodes.get(node + 2), repr, prefix);
        }
        return repr;
    }
    
    // JH: Just to repeat, do not use bytes, but chars.
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
