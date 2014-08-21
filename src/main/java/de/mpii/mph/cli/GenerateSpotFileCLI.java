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

/**
 * Main App!
 * 
 */
public class GenerateSpotFileCLI {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("i", "input", true,
				"UTF-8 file with one 'name<TAB>id' pair per line, sorted by id");
		options.addOption("o", "output", true, "spotfile");
		options.addOption("e", "eliasfano", true, "elias fano compressed index");
		// options.addOption("t", "threshold", true,
		// "Threshold to use for truncating tokens to prefix");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
		File inputPath = new File(cmd.getOptionValue("i"));
		File outputPath = new File(cmd.getOptionValue("o"));
		File efPath = new File(cmd.getOptionValue("e"));
		File tmp = null;
		try {
			tmp = File.createTempFile("eliasfano-offset", ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmp.deleteOnExit();

		RamSpotFile spotFile = new RamSpotFile();
		spotFile.dumpSpotFile(inputPath, outputPath, tmp);
		SpotEliasFanoOffsets offsets = new SpotEliasFanoOffsets()
				.generateEliasFanoFile(tmp.getAbsolutePath());
		offsets.dump(efPath);

	}
}
