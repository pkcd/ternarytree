package de.mpii.mph.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import de.mpii.mph.SpotMinimalPerfectHash;

/**
 * Main App!
 * 
 */
public class GenerateMinimalPerfectHashCLI {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("i", "input", true,
				"UTF-8 file with one 'name<TAB>id' pair per line");
		options.addOption("o", "output", true, "MPH file 'name<TAB>mph-value");
		options.addOption("h", "hash", true, "serialized mph file");

		// options.addOption("t", "threshold", true,
		// "Threshold to use for truncating tokens to prefix");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
		File inputPath = new File(cmd.getOptionValue("i"));
		File outputPath = new File(cmd.getOptionValue("o"));
		File mphPath = new File(cmd.getOptionValue("h"));
		System.out.println("mphPath = " + mphPath);
		System.out.println("outputPath = " + outputPath);

		SpotMinimalPerfectHash mph = new SpotMinimalPerfectHash()
				.generateHash(inputPath);
		mph.dumpSpotsAndHash(inputPath, outputPath);
		mph.dump(mphPath);

	}
}
