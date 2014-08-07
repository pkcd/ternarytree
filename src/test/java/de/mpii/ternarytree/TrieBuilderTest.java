package de.mpii.ternarytree;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TrieBuilderTest {

	@Test
	public void buildTest() throws IOException {
		TernaryTriePrimitive trie = new TernaryTriePrimitive();
		trie.put("A", 1);
		trie.put("A C", 2);
		trie.put("A C", 3);
		trie.put("B", 4);

		assertEquals(1, trie.get("A"));
		assertEquals(3, trie.get("A C"));
		assertEquals(4, trie.get("B"));

		File tmpFile = File.createTempFile("trie", "tmp");
		tmpFile.deleteOnExit();
		TrieBuilder tb = new TrieBuilder();
		tb.write(trie, tmpFile);
		TernaryTriePrimitive readTrie = tb.loadTernaryTriePrimitive(tmpFile);
		assertEquals(1, readTrie.get("A"));
		assertEquals(3, readTrie.get("A C"));
		assertEquals(4, readTrie.get("B"));
	}

	@Test
	public void testSerialize() throws FileNotFoundException, IOException {
		TernaryTriePrimitive t = new TernaryTriePrimitive(1.0);
		TrieBuilder tb = new TrieBuilder();

		File tmp = File.createTempFile("test", ".bin");
		t.put("pippo", 0);
		t.put("pluto", 1);
		t.put("paris", 2);
		tb.write(t, tmp);

		Trie s = tb.loadTernaryTriePrimitive(tmp);

		assertEquals(0, s.get("pippo"));
		assertEquals(1, s.get("pluto"));
		assertEquals(2, s.get("paris"));

		s = tb.loadTernaryTriePrimitive(tmp);

		assertEquals(0, s.get("pippo"));
		assertEquals(1, s.get("pluto"));
		assertEquals(2, s.get("paris"));
		// System.out.println(t.getContent());
	}
}
