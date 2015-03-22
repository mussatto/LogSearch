package com.curupira.index;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class IndexerTest {

    @Test
    public void testIndexData() throws Exception {

        Indexer indexer = Indexer.create("luceneIndex");
        addTestData(indexer);

        SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

        List<String> results = simpleSearch.search("exception");

        for(String result : results){
            System.out.println(result);
        }

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
