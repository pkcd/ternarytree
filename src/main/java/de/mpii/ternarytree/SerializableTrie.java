package de.mpii.ternarytree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface to add serialization/deserialization capabilities to a tree.
 * 
 */
public interface SerializableTrie {

    /**
     * This method serializes the current state of the structure into an
     * outputstream.
     * 
     * @param stream
     *            The stream where the state has to be written
     * @throws IOException
     *             If there is an error while writing to stream.
     */
    public void serialize(OutputStream stream) throws IOException;

    /**
     * This method recreates the structure along with its state from the
     * serialized output
     * 
     * @param stream
     *            The stream containing output from serialize method.
     * @throws IOException
     *             If there is an error while reading from stream.
     */
    public Trie deserialize(InputStream stream) throws IOException;

}
