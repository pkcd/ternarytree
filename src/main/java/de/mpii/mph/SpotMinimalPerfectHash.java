/**
 *  Copyright 2013 Diego Ceccarelli
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.mpii.mph;

import it.cnr.isti.hpc.io.IOUtils;
import it.cnr.isti.hpc.io.Serializer;
import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.sux4j.mph.MinimalPerfectHashFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 8, 2013
 */
public class SpotMinimalPerfectHash {

	private static final Logger logger = LoggerFactory
			.getLogger(SpotMinimalPerfectHash.class);

	public static final String STDNAME = "mph.bin";
	public static final String STDSPOTNAME = "spot-mph.tsv";

	private MinimalPerfectHashFunction<String> mph;

	public long hash(String spot) {
		return mph.getLong(spot);
	}

	public SpotMinimalPerfectHash generateHash(File spotFile) {
		SpotIterable iterator = new SpotIterable(spotFile);

		try {
			MinimalPerfectHashFunction.Builder<String> builder = new MinimalPerfectHashFunction.Builder<String>();
			builder.transform(TransformationStrategies.utf16());
			builder.keys(iterator);

			mph = builder.build();
		} catch (IOException e) {
			logger.error("generating minimal perfect hash ({}) ", e.toString());
			System.exit(-1);
		}
		return this;
	}

	public void dumpSpotsAndHash(File spotFile, File output) {
		logger.info("output spots in {}", output.getAbsolutePath());
		SpotIterable iterator = new SpotIterable(spotFile);
		BufferedWriter writer = IOUtils.getPlainOrCompressedUTF8Writer(output
				.getAbsolutePath());
		for (String spot : iterator) {
			try {
				writer.write(spot);
				writer.write('\t');

				writer.write(String.valueOf(hash(spot)));

				writer.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dump(File outputFile) {
		logger.info("dump minimal perfect hashing in {} ", outputFile);

		Serializer serializer = new Serializer();
		serializer.dump(mph, outputFile.getAbsolutePath());
	}

	public SpotMinimalPerfectHash load(File file) {
		Serializer serializer = new Serializer();
		logger.info("loading minimal perfect hashing in {} ",
				file.getAbsolutePath());

		mph = (MinimalPerfectHashFunction<String>) serializer.load(file
				.getAbsolutePath());
		return this;

	}

	public static class SpotIterable implements Iterable<String> {

		File spotFile;

		public SpotIterable(File spotFile) {
			this.spotFile = spotFile;
		}

		public Iterator<String> iterator() {
			return new SpotIterator(spotFile);
		}

	}

	private static class SpotIterator implements Iterator<String> {
		BufferedReader br = null;
		String nextLine = null;

		public SpotIterator(File spotFile) {
			br = IOUtils.getPlainOrCompressedUTF8Reader(spotFile
					.getAbsolutePath());
			try {
				nextLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean hasNext() {
			return nextLine != null;
		}

		public String next() {
			Scanner scanner = new Scanner(nextLine).useDelimiter("\t");
			String next = scanner.next();
			try {
				nextLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return next;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
