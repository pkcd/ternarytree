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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Diego Ceccarelli <diego.ceccarelli@isti.cnr.it>
 * 
 *         Created on Mar 8, 2013
 */
public class RamSpotRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(RamSpotRepository.class);

	RamSpotFile spots;
	SpotMinimalPerfectHash hash;
	SpotEliasFanoOffsets offsets;

	public RamSpotRepository(File mphDir) {
		hash = new SpotMinimalPerfectHash().load(new File(mphDir,
				SpotMinimalPerfectHash.STDNAME));
		offsets = new SpotEliasFanoOffsets().load(new File(mphDir,
				SpotEliasFanoOffsets.STDNAME));
		spots = new RamSpotFile().loadSpotFile(new File(mphDir,
				RamSpotFile.STDNAME));

	}

	public RamSpotRepository(File mphFile, File efFile, File spotFile) {
		hash = new SpotMinimalPerfectHash().load(mphFile);
		offsets = new SpotEliasFanoOffsets().load(efFile);
		spots = new RamSpotFile().loadSpotFile(spotFile);

	}

	public long getId(String spot) {

		long index = hash.hash(spot);
		// System.out.println("hash(" + spot + ")=" + index);
		if (index < 0) {
			return -1;
		}

		long from = offsets.getOffset(index);
		long to = offsets.getOffset(index + 1);

		// logger.info("offsetStart = {} ",from);
		// logger.info("offsetEnd = {} ",to);
		byte[] binspot = spots.getOffset(from, to);
		String match = new String(binspot);
		// System.out.println("match =" + match);
		if (match.equals(spot))
			return index;
		return -1;

	}
}
