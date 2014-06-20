package de.mpii.ternarytree;

import gnu.trove.list.TIntList;

/**
 * This interface describes a trie data structure. It maintains
 * a set of integers against an ascii string key.
*/
public interface Trie {

    /**
     * This method returns the list of integer values associated with a key.
     * @param key An ascii string or the key
     * @return The list of integers associated with the key. This is empty if 
     * the key does not exist.
     */
    public TIntList get(String key);
    
    /**
     * This method adds a (key, value) pair into the data structure. This
     * does nothing when the given (key, value) pair already exists
     * @param key An ascii string or the key
     * @param value An integer or the value
     */
    public void add(String key, int value);
    
    /**
     * Returns a string representation of the trie. It is a sequence of lines of
     * the form "key, values". The lines are ordered as in depth first traversal
     * where left, equal and right childs are given decreasing priorities.
     */
    @Override
    public String toString();
}
