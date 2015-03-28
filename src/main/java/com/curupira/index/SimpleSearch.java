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

public class SimpleSearch {

    //private IndexReader reader;

    private IndexSearcher indexSearcher;

    private TopScoreDocCollector collector;

    public SimpleSearch(Directory directory) throws IOException {

        IndexReader reader = DirectoryReader.open(directory);

        this.indexSearcher = new IndexSearcher(reader);

        this.collector = TopScoreDocCollector.create(50, true);

    }

    public ScoreDoc[] search(String queryString) throws ParseException, IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        Query query = new QueryParser(TOKENS.LOG_LINE_TOKEN.toString(),analyzer)
                .parse(queryString);

        indexSearcher.search(query, collector);

        return collector.topDocs().scoreDocs;
    }

    public String[] searchString(String queryString) throws ParseException, IOException {

        ScoreDoc[] hits = search(queryString);
        String[] hitsString = new String[hits.length];

        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document doc = indexSearcher.doc(docId);
            hitsString[i] = stringfy(doc);
        }

        return hitsString;
    }

    public static String stringfy(Document doc){
        return doc.get(TOKENS.FILENAME_TOKEN.toString()) +
                ","+doc.get(TOKENS.LOG_LINE_NUMBER_TOKEN.toString())+
                ": "+ doc.get(TOKENS.LOG_LINE_TOKEN.toString());
    }
}
