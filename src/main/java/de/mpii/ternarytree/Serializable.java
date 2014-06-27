package de.mpii.ternarytree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializable {

    /**
     * This method serializes the current state of the trie into an
     * outputstream.
     * 
     * @param stream
     *            The stream where the state has to be written
     * @throws IOException
     *             If there is an error while writing to stream.
     */
    public void serialize(OutputStream stream) throws IOException;

    /**
     * This method replaces the contents of the trie with the serialized output
     * from input stream.
     * 
     * @param stream
     *            The stream containing output from serialize method.
     * @throws IOException
     *             If there is an error while reading from stream.
     */
    public void deserialize(InputStream stream) throws IOException;

}
