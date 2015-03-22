package com.curupira.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleSearch {

    //private IndexReader reader;

    private IndexSearcher indexSearcher;

    private TopScoreDocCollector collector;

    public SimpleSearch(Directory directory) throws IOException {

        IndexReader reader = DirectoryReader.open(directory);

        this.indexSearcher = new IndexSearcher(reader);

        this.collector = TopScoreDocCollector.create(50, true);

    }

    public List<String> search(String queryString) throws ParseException, IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        Query query = new QueryParser(TOKENS.LOG_LINE_TOKEN.toString(),analyzer)
                .parse(queryString);

        indexSearcher.search(query, collector);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");

        List<String> results = new ArrayList<String>();
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            results.add(
                    d.get(TOKENS.FILENAME_TOKEN.toString()) +
                            ","+d.get(TOKENS.LOG_LINE_NUMBER_TOKEN.toString())+
                            ": "+ d.get(TOKENS.LOG_LINE_TOKEN.toString()) );
        }

        return results;
    }
}
