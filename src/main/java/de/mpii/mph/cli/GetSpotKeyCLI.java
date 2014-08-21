package de.mpii.mph.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import de.mpii.mph.RamSpotRepository;

/**
 * Main App!
 * 
 */
public class GetSpotKeyCLI {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("h", "mph", true, "the minimal perfect hashing file");
		options.addOption("s", "spot", true, "spotfile");
		options.addOption("e", "eliasfano", true, "elias fano compressed index");
		options.addOption("q", "query", true, "spot");
		// options.addOption("t", "threshold", true,
		// "Threshold to use for truncating tokens to prefix");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
		File mphPath = new File(cmd.getOptionValue("h"));
		File spotfilePath = new File(cmd.getOptionValue("s"));
		File efPath = new File(cmd.getOptionValue("e"));
		String query = cmd.getOptionValue("q");
		RamSpotRepository repo = new RamSpotRepository(mphPath, efPath,
				spotfilePath);
		System.out.println(query + ": " + repo.getId(query));

	}
}
