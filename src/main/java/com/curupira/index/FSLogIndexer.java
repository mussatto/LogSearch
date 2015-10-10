package com.curupira.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class FSLogIndexer extends LogIndexer {

    public FSLogIndexer(String path) {
        super(path);
    }

    @Override
    void createIndex(String indexPath) throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory directory;
        File file = new File(indexPath);
        directory = new SimpleFSDirectory(file);

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);

        this.directory = directory;
        this.writer = new IndexWriter(directory, indexWriterConfig);
    }
}
