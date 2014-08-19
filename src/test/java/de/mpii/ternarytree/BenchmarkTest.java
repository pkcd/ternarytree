package de.mpii.ternarytree;

import static org.junit.Assert.*;

import org.junit.Test;

public class BenchmarkTest {

    @Test
    public void testMeasureBuildTime() {
        Benchmark b = new Benchmark(getClass().getResourceAsStream("/sample.tsv"));
        TernaryTriePrimitive trie = new TernaryTriePrimitive();
        double buildTime = b.measureBuildTime(trie);
        assertEquals(true, buildTime > 0);
        System.out.println("Trie build time is " + buildTime + " s");
    }
    
    @Test
    public void testMeasureSpotTime() {
        Benchmark b = new Benchmark(getClass().getResourceAsStream("/sample.tsv"));
        TernaryTriePrimitive trie = new TernaryTriePrimitive();
        double matchingTime = b.measureSpotTime(trie);
        assertEquals(true, matchingTime > 0);
        System.out.println("Trie matching time is " + matchingTime + " s");
    }
}
