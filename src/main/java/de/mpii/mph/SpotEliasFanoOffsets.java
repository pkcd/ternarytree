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

import it.cnr.isti.hpc.io.Serializer;
import it.cnr.isti.hpc.io.reader.RecordReader;
import it.cnr.isti.hpc.io.reader.TsvRecordParser;
import it.cnr.isti.hpc.io.reader.TsvTuple;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.sux4j.util.EliasFanoMonotoneLongBigList;

import java.io.File;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 8, 2013
 */
public class SpotEliasFanoOffsets {

	private static final Logger logger = LoggerFactory
			.getLogger(SpotEliasFanoOffsets.class);

	public static final String STDNAME = "elias-fano-index.bin";

	private EliasFanoMonotoneLongBigList ef = null;

	public SpotEliasFanoOffsets load(File offsetsBinFile) {
		Serializer serializer = new Serializer();
		ef = (EliasFanoMonotoneLongBigList) serializer.load(offsetsBinFile
				.getAbsolutePath());

		return this;

	}

	public long getOffset(long index) {
		return ef.getLong(index);
	}

	public SpotEliasFanoOffsets generateEliasFanoFile(String offsetsFile) {
		ef = new EliasFanoMonotoneLongBigList(new OffsetsFile(offsetsFile));
		return this;
	}

	public void dump(File outputFile) {
		Serializer serializer = new Serializer();
		logger.info("serializing EliasFano in {} ", outputFile);
		serializer.dump(ef, outputFile.getAbsolutePath());
	}

	public static class OffsetsFile implements LongIterable {

		private final String file;

		public OffsetsFile(String file) {
			this.file = file;

		}

		public LongIterator iterator() {
			return new OffsetsFileIterator(file);
		}

	}

	public static class OffsetsFileIterator implements LongIterator {

		Iterator<TsvTuple> iterator;
		public static final String FIELD = "offset";

		public OffsetsFileIterator(String file) {
			RecordReader<TsvTuple> reader = new RecordReader<TsvTuple>(file,
					new TsvRecordParser(FIELD));
			iterator = reader.iterator();
		}

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public Long next() {
			return Long.parseLong(iterator.next().get(FIELD));
		}

		public void remove() {
			iterator.remove();

		}

		public long nextLong() {
			return next();
		}

		public int skip(int arg0) {
			throw new UnsupportedOperationException();
		}

	}

}
