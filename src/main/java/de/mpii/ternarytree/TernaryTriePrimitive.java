package de.mpii.ternarytree;

import java.io.UnsupportedEncodingException;

import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;

public class TernaryTriePrimitive implements Trie{
    TByteList labels;
    TIntList nodes;
    TIntList values;
    int root;
    
    public TernaryTriePrimitive() {
        labels = new TByteArrayList();
        nodes = new TIntArrayList();
        values = new TIntArrayList();
        root = -1;
    }

    public TIntList get(String keyString) {
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
        return getValueList(node);
    }
    
    public void add(String key, int value) {
        byte[] bytes = getBytes(key);
        root = add(root, bytes, 0, value);
    }
    
    private int add(int node, byte[] chars, int pos, int value) {
        byte chr = chars[pos];
        if (node == -1) {
            node = nodes.size();
            nodes.add(new int[]{-1, -1, -1, -1});
            labels.add(chr);
        }
        if (chr < labels.get(node/4)) {
            nodes.set(node, add(nodes.get(node), chars, pos, value));
        } else if (chr == labels.get(node/4)) {
            if (pos < chars.length  - 1) {
                nodes.set(node + 1, add(nodes.get(node + 1), chars, pos + 1, value));
            } else {
                int list = nodes.get(node + 3);
                if (list == -1) {
                    list = values.size();
                    nodes.set(node + 3, list);
                    values.add(new int[]{list + 2, value, -1});
                } else {
                    int end = values.get(list);
                    values.set(end, values.size());
                    values.set(list, values.size() + 1);
                    values.add(new int[]{value, -1});
                }
            }
        } else {
             nodes.set(node + 2, add(nodes.get(node + 2), chars, pos, value));
        }
        return node;
    }
    
    @Override
    public String toString() {
        String repr = toString(root, "", "");
        return repr;
    }
    
    private String toString(int node, String repr, String prefix) {
        if (node != -1) {
            if (nodes.get(node + 3) != -1) {
                repr += prefix + (char)labels.get(node/4) + " , " + getValueList(node).toString() + "\n";
            }
            repr = toString(nodes.get(node), repr, prefix);
            repr = toString(nodes.get(node + 1), repr, prefix + (char)labels.get(node/4));
            repr = toString(nodes.get(node + 2), repr, prefix);
        }
        return repr;
    }
    
    private TIntList getValueList(int node) {
        TIntList mappedValues = new TIntArrayList();
        if (node != -1) {
            int listNode = nodes.get(node + 3);
            if (listNode != -1) {
                listNode++;
                while(listNode != -1) {
                    mappedValues.add(values.get(listNode));
                    listNode++;
                    listNode = values.get(listNode);
                }
            }
        }
        return mappedValues;
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
