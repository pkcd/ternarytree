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
		options.addOption("i", "mph-dir", true,
				"the minimal perfect hashing spotter dir");
		options.addOption("q", "query", true, "spot");
		// options.addOption("t", "threshold", true,
		// "Threshold to use for truncating tokens to prefix");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		File dir = new File(cmd.getOptionValue("i"));
		String query = cmd.getOptionValue("q");
		RamSpotRepository repo = new RamSpotRepository(dir);
		System.out.println(query + ": " + repo.getId(query));

	}
}
