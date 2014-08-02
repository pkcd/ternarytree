package de.mpii.ternarytree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Loads and writes Tries from and to disk.
 */
public class TrieBuilder {
  public void write(SerializableTrie trie, File file) {
    
  }
  
  public TernaryTriePrimitive loadTernaryTriePrimitive(File file) throws FileNotFoundException, IOException {
    TernaryTriePrimitive trie = new TernaryTriePrimitive();
    trie.deserialize(new FileInputStream(file));
    return trie;
  }
}
