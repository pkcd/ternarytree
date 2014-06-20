package de.mpii.ternarytree;

import java.io.UnsupportedEncodingException;

import gnu.trove.list.TByteList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TIntArrayList;

public class TernaryTriePrimitive {
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

    /**
     * This method returns the list of integer values associated with a key.
     * @param key An ascii string or the key
     * @return The list of integers associated with the key. This is empty if 
     * the key does not exist.
     */
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
    
    /**
     * This method adds a (key, value) pair into the data structure. This
     * does nothing when the given (key, value) pair already exists
     * @param key An ascii string or the key
     * @param value An integer or the value
     */
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
