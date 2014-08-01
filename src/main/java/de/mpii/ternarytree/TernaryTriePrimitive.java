package de.mpii.ternarytree;

import gnu.trove.list.TCharList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.list.array.TIntArrayList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TernaryTriePrimitive implements Trie, Serializable<Trie>{
  
    private static final int FORMAT_VERSION = 1;
  
    private TCharList labels;
    private TIntList nodes;
    private int root;
    private double threshold;
    private char delimiter;
    
    public TernaryTriePrimitive(double t) {
        this(t, ' ');
    }
    
    public TernaryTriePrimitive(double t, char d) {
        labels = new TCharArrayList();
        nodes = new TIntArrayList();
        root = -1;
        threshold = t;
        delimiter = d;
    }
    
    public TernaryTriePrimitive(File trieFile) throws IOException {
        this.deserialize(new FileInputStream(trieFile));
    }
    
    public Match getLongestMatch(String[] tokens, int start) {
        int node = root;
        int value = -1;
        int iToken = start;
        for(iToken = start; iToken < tokens.length; iToken++) {
            String token = tokens[iToken];
            int pos = 0;
            while (node != -1) {
                if (token.charAt(pos) < getNodeKey(node)) {
                    node = getLessChild(node);
                } else if(token.charAt(pos) == getNodeKey(node)) {
                    if (pos == getRelevantLength(token) - 1) {
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
                break;
            } else{
                //match delimiter
                value = getNodeValue(node);
                node = getEqualChild(node);
                if (iToken < tokens.length - 1) {
                    if (delimiter < getNodeKey(node)) {
                        node = getLessChild(node);
                    } else if(delimiter == getNodeKey(node)) {
                        node = getEqualChild(node);
                    } else {
                        node = getGreatChild(node);
                    }
                }
            }
        }
        return new Match(start, iToken - start, value);
    }
    
    public int get(String[] tokens) {
        Match match = this.getLongestMatch(tokens, 0);
        if (match.getTokenCount() == tokens.length) {
            return match.getValue();
        } else {
            return -1;
        }
    }
        
    public int get(String key) {
        return get(key.split(String.valueOf(delimiter)));
    }
    
    public void put (String[] tokens, int value) {
        root = put(root, tokens, 0, 0, value);
    }
    
    public void put(String key, int value) {
        root = put(root, key.split(String.valueOf(delimiter)), 0, 0, value);
    }
    
    private int put(int node, String[] tokens, int iToken, int pos, int value) {
        int length = getRelevantLength(tokens[iToken]);
        char chr = delimiter;
        if (pos < length) {
            chr = tokens[iToken].charAt(pos);
        }
        if (node == -1) {
            node = getNewNode(chr);
        }
        if (chr < getNodeKey(node)) {
            setLessChild(node, put(getLessChild(node), tokens, iToken, pos, value));
        } else if (chr == getNodeKey(node)) {
            if (iToken < tokens.length - 1) {
                if (pos <= length  - 1) {
                    setEqualChild(node, put(getEqualChild(node), tokens, iToken, pos + 1, value));
                } else {
                    setEqualChild(node, put(getEqualChild(node), tokens, iToken + 1, 0, value));
                }
            } else {
                if (pos < length - 1){
                    setEqualChild(node, put(getEqualChild(node), tokens, iToken, pos + 1, value));
                } else {
                    setNodeValue(node, value);
                }
            }
        } else {
             setGreatChild(node, put(getGreatChild(node), tokens, iToken, pos, value));
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
        StringBuilder repr = getContent(root, new StringBuilder(), "");
        return repr.toString();
    }
    
    private StringBuilder getContent(int node, StringBuilder repr, String prefix) {
        if (node != -1) {
            if (nodes.get(node + 3) != -1) {
                repr.append(prefix + labels.get(node/4) + "\t" + String.valueOf(nodes.get(node + 3)) + "\n");
            }
            repr = getContent(nodes.get(node), repr, prefix);
            repr = getContent(nodes.get(node + 1), repr, prefix + labels.get(node/4));
            repr = getContent(nodes.get(node + 2), repr, prefix);
        }
        return repr;
    }

    private int getRelevantLength(String key) {
        return (int)Math.ceil(key.length() * threshold);
    }
    
    public void serialize(OutputStream stream) throws IOException {
        DataOutputStream writer = new DataOutputStream(
                new BufferedOutputStream(stream));
        writer.writeInt(FORMAT_VERSION);
        writer.writeDouble(threshold);
        writer.writeChar(delimiter);
        writer.writeInt(nodes.size());
        for (int i = 0; i < nodes.size(); i++) {
            writer.writeInt(nodes.get(i));
        }
        writer.writeInt(labels.size());
        for (int i = 0; i < labels.size(); i++) {
            writer.writeChar(labels.get(i));
        }
        writer.close();
    }

    public Trie deserialize(InputStream stream) throws IOException {
        DataInputStream reader = new DataInputStream(new BufferedInputStream(stream));
        nodes.clear();
        labels.clear();
        reader.readInt(); //discard version
        threshold = reader.readDouble();
        delimiter = reader.readChar();
        int numNodes = reader.readInt();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(reader.readInt());
        }
        int numLabels = reader.readInt();
        for (int i = 0; i < numLabels; i++) {
            labels.add(reader.readChar());
        }
        return this;
    }
}

