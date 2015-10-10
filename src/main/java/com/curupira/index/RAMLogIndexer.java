package com.curupira.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class RAMLogIndexer extends LogIndexer {

    public RAMLogIndexer(String path) {
        super(path);
    }

    @Override
    void createIndex(String indexPath) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory directory;
        
        directory = new RAMDirectory();


        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);

        this.directory = directory;
        this.writer = new IndexWriter(directory, indexWriterConfig);
    }
}
