package de.mpii.ternarytree;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializable {

    /**
     * This method serializes the current state of the trie into an
     * outputstream.
     * 
     * @param stream
     *            The stream where the state has to be written
     */
    public void serialize(OutputStream stream);

    /**
     * This method replaces the contents of the trie with the serialized output
     * from input stream.
     * 
     * @param stream
     *            The stream containing output from serialize method.
     */
    public void deserialize(InputStream stream);

}
