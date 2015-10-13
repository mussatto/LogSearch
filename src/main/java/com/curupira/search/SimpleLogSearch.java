package com.curupira.search;

import com.curupira.document.LogSearchDocument;
import com.curupira.index.TOKENS;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

public class SimpleLogSearch implements LogSearch{

    //private IndexReader reader;

    private IndexSearcher indexSearcher;

    public SimpleLogSearch(Directory directory) throws IOException {

        IndexReader reader = DirectoryReader.open(directory);

        this.indexSearcher = new IndexSearcher(reader);

    }

    private ScoreDoc[] search(String queryString) throws ParseException, IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        Query query = new QueryParser(TOKENS.LOG_LINE_TOKEN.toString(),
                analyzer)
                .parse(queryString);

        TopScoreDocCollector collector = TopScoreDocCollector.create(50, true);
        indexSearcher.search(query, collector);

        return collector.topDocs().scoreDocs;
    }

    public List<LogSearchDocument> searchString(String queryString) throws ParseException, IOException {

        ScoreDoc[] hits = search(queryString);
        List<LogSearchDocument> results = new ArrayList<>();

        int i=0;

        for(ScoreDoc scoreDoc : hits) {
            int docId = scoreDoc.doc;
            results.add(new LogSearchDocument(indexSearcher.doc(docId)));
            i++;
        }

        return results;
    }


}
