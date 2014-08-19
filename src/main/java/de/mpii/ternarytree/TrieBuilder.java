package de.mpii.ternarytree;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.iq80.snappy.SnappyInputStream;
import org.iq80.snappy.SnappyOutputStream;

/**
 * Loads and writes Tries from and to disk.
 * Uses Snappy for compression.
 */
public class TrieBuilder {
  public void write(SerializableTrie trie, File file) throws FileNotFoundException, IOException {
    trie.serialize(new BufferedOutputStream(new SnappyOutputStream(new FileOutputStream(file)), 2<<20));
  }
  
  public TernaryTriePrimitive loadTernaryTriePrimitive(File file) throws FileNotFoundException, IOException {
    TernaryTriePrimitive trie = new TernaryTriePrimitive();
    trie.deserialize(new BufferedInputStream(new SnappyInputStream(new FileInputStream(file)), 2<<20));
    return trie;
  }
}
