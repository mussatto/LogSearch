package com.curupira.index;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

public class IndexerTest {

    @Test
    public void testIndexDataMemory() throws Exception {

        Indexer indexer = Indexer.createInMemory("luceneIndex");
        addTestData(indexer);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString("exception");

        for(String result : results){
            System.out.println(result);
        }

    }

    @Test
    public void testIndexDataFileSystem() throws Exception {

        Indexer indexer = Indexer.createInFileSystemExisting("luceneIndex.index");
        addTestData(indexer);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString("exception");

        for(String result : results){
            System.out.println(result);
        }

        indexer.close();

    }

    @Test
    public void testIndexFileSystemExisting() throws Exception{
        Indexer indexer = Indexer.createInFileSystemExisting("luceneIndex.index");

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        String[] results = simpleSearch.searchString("exception");

        for(String result : results){
            System.out.println(result);
        }

        indexer.close();
    }

    private void addTestData(Indexer indexer) throws IOException {
        java.net.URL url = this.getClass().getResource("/sample.log");
        long start = new Date().getTime();
        System.out.println("Loading file:"+url.getFile());
        FileInputter fileInputter = new FileInputter(indexer, url.getPath());

        fileInputter.feedToIndex();

        long diff = new Date().getTime() - start;

        System.out.println("Indexed file in " + diff +" miliseconds.");
    }


}
