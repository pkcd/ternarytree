package de.mpii.ternarytree;

import java.util.Map;
import java.util.Map.Entry;

public class Spotter {

    private TernaryTriePrimitive trie;

    public Spotter() {
        trie = new TernaryTriePrimitive();
    }

    /**
     * 
     * @param tokens
     *            An map of mentions along with ids that must be recognized in a
     *            document.
     * @return An object that can be used to spot the these tokens in a
     *         document.
     */
    public void build(Map<String, Integer> tokens) {
        for (Entry<String, Integer> entry : tokens.entrySet()) {
            trie.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 
     * @param tokens
     *            The document as a string of tokens
     * @return A map describing the spotted entities. The key and value is
     *         offset and count of the match.
     */
    public Map<Integer, Integer> findAllSpots(String[] tokens) {
        return trie.getAllMatches(tokens);
    }

}
