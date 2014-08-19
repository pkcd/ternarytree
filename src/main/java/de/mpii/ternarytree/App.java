package de.mpii.ternarytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Main App!
 * 
 */
public class App {
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("i", "input", true, "UTF-8 file with one 'name<TAB>id' pair per line");
        options.addOption("o", "output", true, "Path to output serialized trie");
        options.addOption("t", "threshold", true, "Threshold to use for truncating tokens to prefix");
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);
        String inputPath = cmd.getOptionValue("i");
        String outputPath = cmd.getOptionValue("o");
        Double threshold = Double.parseDouble(cmd.getOptionValue("t", "1.0"));
        TernaryTriePrimitive t = new TernaryTriePrimitive(threshold);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Files.newInputStream(Paths
                            .get(inputPath))));
            String line = null;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                String key = line.split("\t")[0];
                t.put(key, lineNumber++);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            t.serialize(new GZIPOutputStream(Files.newOutputStream(Paths
                    .get(outputPath))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
