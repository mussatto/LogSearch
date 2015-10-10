package com.curupira.index;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class IndexerTest {

    public static final String INDEX_PATH = "etc/luceneIndex.index";
    public static final String EXCEPTION = "exception";
    public static final String LUCENE_INDEX = "luceneIndex";
    public static final String SAMPLE_LOG = "/sample.log";

    @Test
    public void testIndexDataMemory() throws Exception {

        Indexer indexer = Indexer.createInMemory(LUCENE_INDEX);
        addTestData(indexer);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString(EXCEPTION);

        for(String result : results){
            System.out.println(result);
            assertEquals(true, result.toLowerCase().contains(EXCEPTION));

        }

    }

    @Test
    public void testIndexDataFileSystem() throws Exception {

        Indexer indexer = Indexer.createInFileSystemExisting(INDEX_PATH);
        addTestData(indexer);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString(EXCEPTION);

        for(String result : results){
            System.out.println(result);
            assertEquals(true, result.toLowerCase().contains(EXCEPTION));
        }

        indexer.close();

    }

    @Test
    public void testIndexFileSystemExisting() throws Exception{
        Indexer indexer = Indexer.createInFileSystemExisting(INDEX_PATH);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString(EXCEPTION);

        for(String result : results){
            System.out.println(result);
            assertEquals(true, result.toLowerCase().contains(EXCEPTION));
        }

        indexer.close();
    }

    private void addTestData(Indexer indexer) throws IOException {
        java.net.URL url = this.getClass().getResource(SAMPLE_LOG);
        long start = new Date().getTime();
        System.out.println("Loading file:"+url.getFile());
        FileInputter fileInputter = new FileInputter(indexer, url.getPath());

        fileInputter.feedToIndex();

        long diff = new Date().getTime() - start;

        System.out.println("Indexed file in " + diff +" miliseconds.");
    }


}
