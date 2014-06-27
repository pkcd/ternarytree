package de.mpii.ternarytree;

/**
 * This interface describes a trie data structure. It maintains
 * an integer against a string key.
*/
public interface Trie {

    /**
     * This method returns the integer value associated with a key.
     * @param key, A string or the key
     * @param defaultValue, The value to return if the key doesn't exist
     * @return The integer mapped to the key. This is equal to defaultValue
     * if the key does not exist
     */
    public int get(String key, int defaultValue);
    
    /**
     * This method puts a (key, value) pair into the data structure. This
     * overwrites the previous value when the given key already exists
     * @param key, A string or the key
     * @param value, An integer or the value
     */
    public void put(String key, int value);
    
    /**
     * Returns a string representation of the trie. It is a sequence of lines of
     * the form "key, values". The lines are ordered as in depth first traversal
     * where left, equal and right childs are given decreasing priorities.
     */
    @Override
    public String toString();
}
