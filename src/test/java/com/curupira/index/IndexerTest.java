package com.curupira.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
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
        long start = new Date().getTime();
        FileInputter fileInputter = new FileInputter(indexer, "resources/sample.log");

        fileInputter.feedToIndex();

        long diff = new Date().getTime() - start;

        System.out.println("Indexed file in " + diff +" miliseconds.");
    }


}
