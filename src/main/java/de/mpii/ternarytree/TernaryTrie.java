package de.mpii.ternarytree;

public class TernaryTrie implements Trie{

    private class Node {
        private char chr;
        private Node left, equal, right;
        private int value;
        
        public Node(char chr) {
            this.chr = chr;
            this.value = -1;
            this.left = this.equal = this.right = null;
        }
        
        /**
         * Returns a string representation of a Node. It is a tuple of key
         * character and values i.e "key<tab>value"
         */
        @Override
        public String toString() {
            return chr + "\t" + String.valueOf(value);
        }
    }

    private Node root;
    
    public TernaryTrie() {
        root = null;
    }
    
    public int get(String key) {
        Node p = root;
        int pos = 0;
        while (p != null) {
            if (key.charAt(pos) < p.chr) {
                p = p.left;
            } else if(key.charAt(pos) == p.chr) {
                if (pos == key.length() - 1) {
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
            return p.value;
        }
        return -1;
    }
    
    public void put(String key, int value) {
        root = put(root, key, 0, value);
    }
    
    private Node put(Node p, String key, int pos, int value) {
        char chr = key.charAt(pos);
        if (p == null) {
            p = new Node(chr);
        }
        if (chr < p.chr) {
            p.left = put(p.left, key, pos, value);
        } else if (chr == p.chr) {
            if (pos < key.length()  - 1) {
                p.equal = put(p.equal, key, pos + 1, value);
            } else {
                p.value = value;
            }
        } else {
            p.right = put(p.right, key, pos, value);
        }
        return p;
    }
    
    public String getContent() {
        StringBuilder repr = getContent(root, new StringBuilder(), "");
        return repr.toString();
    }
    
    private StringBuilder getContent(Node p, StringBuilder repr, String prefix) {
        if (p != null) {
            if (p.value != -1) {
                repr.append(prefix + p.toString() + "\n");
            }
            repr = getContent(p.left, repr, prefix);
            repr = getContent(p.equal, repr, prefix + p.chr);
            repr = getContent(p.right, repr, prefix);
        }
        return repr;
    }
    
}
