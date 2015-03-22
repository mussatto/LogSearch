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

public class IndexerTest {

    @Test
    public void testIndexData() throws Exception {

        StandardAnalyzer analyzer = new StandardAnalyzer();

        Indexer indexer = Indexer.create("luceneIndex");
        addTestData(indexer);

        String query = "error";

        Query q = new QueryParser(Indexer.LOG_LINE_TOKEN,analyzer).parse(query);

        IndexReader reader = DirectoryReader.open(indexer.getDirectory());

        IndexSearcher searcher = new IndexSearcher(reader);

        TopScoreDocCollector collector = TopScoreDocCollector.create(50, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        System.out.println("Indexing done!");


        System.out.println("Found " + hits.length + " hits.");

        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get(Indexer.LOG_LINE_NUMBER_TOKEN)+","+ d.get(Indexer.LOG_LINE_TOKEN) );
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
