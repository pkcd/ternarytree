package de.mpii.ternarytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Benchmark {

    private ArrayList<String> document;
    private ArrayList<Boolean> isEntity;
    
    public Benchmark(InputStream inputStream) {
        document = new ArrayList<String>();
        isEntity = new ArrayList<Boolean>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                document.add(tokens[0]);
                if (tokens.length > 1) {
                    isEntity.add(true);
                } else {
                    isEntity.add(false);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param trie to be benchmarked
     * @return build time in seconds.
     */
    public double measureBuildTime(Trie trie) {
        long startTime = System.nanoTime();
        int totalSize = document.size();
        for (int i = 0; i < totalSize; i++) {
            if (isEntity.get(i)) {
                trie.put(document.get(i), i);
            }
        }
        long endTime = System.nanoTime();
        double buildTime = (endTime - startTime)/(1.0*1e9);
        return buildTime;
    }
    
    public double measureSpotTime(TernaryTriePrimitive trie) {
        //build the trie
        int totalSize = document.size();
        for (int i = 0; i < totalSize; i++) {
            if (isEntity.get(i)) {
                trie.put(document.get(i), i);
            }
        }
        String[] tokens = document.toArray(new String[document.size()]);
        //measure time
        long startTime = System.nanoTime();
        trie.getAllMatches(tokens);
        long endTime = System.nanoTime();
        double spotTime = (endTime - startTime)/(1.0*1e9);
        return spotTime;
    }
}
