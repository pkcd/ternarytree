package de.mpii.ternarytree;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.trove.list.TCharList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.list.array.TIntArrayList;

/**
 * JH: Some general comments
 * - please add doc-comments to public methods
 * - please add more comments to parts of the code, e.g. the more higher-up
 *   parts of the while/if constructs to get a quick picture of what is being done.
 *   Even better, follow Diego's suggestion and add all the primitive methods.
 */
public class TernaryTriePrimitive implements Trie, Serializable{
    // JH: Why not use a char list? This way you are independent of the encoding.
    TCharList labels;
    TIntList nodes;
    // JH: Seeing from the unit tests, you allow the same string to be inserted
    // with the same value. I don't think this is necessary, a regular Map-style
    // single key-value should be fine. This way, you can store the single pointer
    // in nodes and do not need values.
    int root;
    
    public TernaryTriePrimitive() {
        labels = new TCharArrayList();
        nodes = new TIntArrayList();
        root = -1;
    }

    public int get(String key, int defaultValue) {
        int node = root;
        int pos = 0;
        while (node != -1) {
            if (key.charAt(pos) < getNodeKey(node)) {
                node = getLessChild(node);
            } else if(key.charAt(pos) == getNodeKey(node)) {
                if (pos == key.length() - 1) {
                    break;
                } else {
                    node = getEqualChild(node);
                    pos++;
                }
            } else {
                node = getGreatChild(node);
            }
        }
        if (node == -1) {
            return defaultValue;
        } else {
            return getNodeValue(node);
        }
    }
    
    public void put(String key, int value) {
        root = put(root, key, 0, value);
    }
    
    private int put(int node, String key, int pos, int value) {
        char chr = key.charAt(pos);
        if (node == -1) {
            node = getNewNode(chr);
        }
        if (chr < getNodeKey(node)) {
            setLessChild(node, put(getLessChild(node), key, pos, value));
        } else if (chr == getNodeKey(node)) {
            if (pos < key.length()  - 1) {
                setEqualChild(node, put(getEqualChild(node), key, pos + 1, value));
            } else {
                setNodeValue(node, value);
            }
        } else {
             setGreatChild(node, put(getGreatChild(node), key, pos, value));
        }
        return node;
    }
    
    private int getLessChild(int node) {
        return nodes.get(node);
    }
    
    private int getEqualChild(int node) {
        return nodes.get(node + 1);
    }
    
    private int getGreatChild(int node) {
        return nodes.get(node + 2);
    }
    
    private int getNodeValue(int node) {
        return nodes.get(node + 3);
    }
    
    private char getNodeKey(int node) {
        return labels.get(node/4);
    }
    
    private int getNewNode(char chr) {
        int newNode = nodes.size();
        for (int i = 0; i < 4; i++) {
            nodes.add(-1);
        }
        labels.add(chr);
        return newNode;
    }
    
    private void setLessChild(int parentNode, int childNode) {
        nodes.set(parentNode, childNode);
    }
    
    private void setEqualChild(int parentNode, int childNode) {
        nodes.set(parentNode + 1, childNode);
    }
    
    private void setGreatChild(int parentNode, int childNode) {
        nodes.set(parentNode + 2, childNode);
    }
    
    private void setNodeValue(int node, int value) {
        nodes.set(node + 3, value);
    }
    
    public String getContent() {
        // JH: It's not a good idea to have the whole trie represented in the
        // toString method - this is also used in the debugger and should be concise,
        // e.g.: TT (X nodes).
        String repr = getContent(root, "", "");
        return repr;
    }
    
    private String getContent(int node, String repr, String prefix) {
        if (node != -1) {
            if (nodes.get(node + 3) != -1) {
                repr += prefix + (char)labels.get(node/4) + " , " + nodes.get(node + 3) + "\n";
            }
            repr = getContent(nodes.get(node), repr, prefix);
            repr = getContent(nodes.get(node + 1), repr, prefix + (char)labels.get(node/4));
            repr = getContent(nodes.get(node + 2), repr, prefix);
        }
        return repr;
    }

    public void serialize(OutputStream stream) throws IOException {
        DataOutputStream writer = new DataOutputStream(
                new BufferedOutputStream(stream));
        writer.writeInt(nodes.size());
        for (int i = 0; i < nodes.size(); i++) {
            writer.writeInt(nodes.get(i));
        }
        writer.writeInt(labels.size());
        for (int i = 0; i < labels.size(); i++) {
            writer.writeChar(labels.get(i));
        }
    }

    public void deserialize(InputStream stream) throws IOException {
        DataInputStream reader = new DataInputStream(new BufferedInputStream(stream));
        nodes.clear();
        labels.clear();
        int numNodes = reader.readInt();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(reader.readInt());
        }
        int numLabels = reader.readInt();
        for (int i = 0; i < numLabels; i++) {
            labels.add(reader.readChar());
        }
    }
}
