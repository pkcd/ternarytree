package de.mpii.mph.cli;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import de.mpii.mph.RamSpotFile;
import de.mpii.mph.SpotEliasFanoOffsets;
import de.mpii.mph.SpotMinimalPerfectHash;

/**
 * Main App!
 * 
 */
public class GenerateSpotFileCLI {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();

		options.addOption("o", "output", true,
				"minimal perfect hashing directory");

		// options.addOption("t", "threshold", true,
		// "Threshold to use for truncating tokens to prefix");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		File outputDir = new File(cmd.getOptionValue("o"));

		File inputPath = new File(outputDir, SpotMinimalPerfectHash.STDSPOTNAME);
		File outputPath = new File(outputDir, RamSpotFile.STDNAME);
		File efPath = new File(outputDir, SpotEliasFanoOffsets.STDNAME);
		File tmp = null;
		try {
			tmp = File.createTempFile("eliasfano-offset", ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmp.deleteOnExit();
		// inputPath.deleteOnExit();
		RamSpotFile spotFile = new RamSpotFile();
		spotFile.dumpSpotFile(inputPath, outputPath, tmp);
		SpotEliasFanoOffsets offsets = new SpotEliasFanoOffsets()
				.generateEliasFanoFile(tmp.getAbsolutePath());
		offsets.dump(efPath);

	}
}
