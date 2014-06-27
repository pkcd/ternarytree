package de.mpii.ternarytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

/**
 * Main App!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "AIDA!" );
        String filePath = args[0];
        String outPath = args[1];
        TernaryTriePrimitive t = new TernaryTriePrimitive();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(Paths.get(filePath)))));
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
            t.serialize(Files.newOutputStream(Paths.get(outPath)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
