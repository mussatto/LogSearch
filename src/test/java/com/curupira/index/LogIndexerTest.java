package com.curupira.index;

import com.curupira.document.LogSearchDocument;
import com.curupira.search.SimpleLogSearch;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class LogIndexerTest {

    public static final String INDEX_PATH = "etc/luceneIndex.index";
    public static final String EXCEPTION = "exception";
    public static final String LUCENE_INDEX = "luceneIndex";
    public static final String SAMPLE_LOG = "/sample.log";

    @Test
    public void testIndexDataMemory() throws Exception {

        LogIndexer logIndexer = LogIndexer.createInMemory(LUCENE_INDEX);
        addTestData(logIndexer);

        SimpleLogSearch simpleLogSearch = new SimpleLogSearch(logIndexer.getDirectory());

        List<LogSearchDocument> results = simpleLogSearch.searchString(EXCEPTION);

        for(LogSearchDocument result : results){
            System.out.println(result);
            assertEquals(true, result.getSearchResult().toLowerCase().contains(EXCEPTION));

        }

    }

    @Test
    public void testIndexDataFileSystem() throws Exception {

        LogIndexer logIndexer = LogIndexer.createInFileSystemExisting(INDEX_PATH);
        addTestData(logIndexer);

        SimpleLogSearch simpleLogSearch = new SimpleLogSearch(logIndexer.getDirectory());

        List<LogSearchDocument> results = simpleLogSearch.searchString(EXCEPTION);

        for(LogSearchDocument result : results){
            System.out.println(result);
            assertEquals(true, result.getSearchResult().toLowerCase().contains(EXCEPTION));
        }

        logIndexer.close();

    }

    @Test
    public void testIndexFileSystemExisting() throws Exception{
        LogIndexer logIndexer = LogIndexer.createInFileSystemExisting(INDEX_PATH);

        SimpleLogSearch simpleLogSearch = new SimpleLogSearch(logIndexer.getDirectory());

        List<LogSearchDocument> results = simpleLogSearch.searchString(EXCEPTION);

        for(LogSearchDocument result : results){
            System.out.println(result);
            assertEquals(true, result.getSearchResult().toLowerCase().contains(EXCEPTION));
        }

        logIndexer.close();
    }

    private void addTestData(LogIndexer logIndexer) throws IOException {
        java.net.URL url = this.getClass().getResource(SAMPLE_LOG);
        long start = new Date().getTime();
        System.out.println("Loading file:"+url.getFile());
        FileInputter fileInputter = new FileInputter(logIndexer, url.getPath());

        fileInputter.feedToIndex();

        long diff = new Date().getTime() - start;

        System.out.println("Indexed file in " + diff +" miliseconds.");
    }


}
