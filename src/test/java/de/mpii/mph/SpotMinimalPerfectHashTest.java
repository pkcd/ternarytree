package de.mpii.mph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.cnr.isti.hpc.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SpotMinimalPerfectHashTest {

	@Test
	public void testMPH() throws IOException {
		File spotFile = File.createTempFile("mph", ".tmp");
		spotFile.deleteOnExit();
		BufferedWriter bw = IOUtils.getPlainOrCompressedUTF8Writer(spotFile
				.getAbsolutePath());
		String[] spots = { "diego", "pisa", "paris", "napoleone", "lol" };
		for (String s : spots) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();
		SpotMinimalPerfectHash mph = new SpotMinimalPerfectHash();
		mph.generateHash(spotFile);
		int[] hashcodes = new int[spots.length];
		for (String s : spots) {
			int h = (int) mph.hash(s);
			System.out.println(s + "\t" + h);
			assertTrue(h >= 0 && h <= 4);
			hashcodes[h]++;
		}
		for (int i = 0; i < hashcodes.length ; i++ ){
			assertEquals(1, hashcodes[i]);
		}
		
	}
}
