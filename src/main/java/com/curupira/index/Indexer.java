package com.curupira.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class Indexer {

    private final String indexPath;

    private IndexWriter writer;

    private Directory directory;

    private long currentLogLineNumber;

    public static Indexer create(String indexPath){

        return new Indexer(indexPath);
    }

    private Indexer(String path){
        this.indexPath=path;
        try {
            createIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void indexLogLine(String logLine, String fileName) throws IOException {
        if(writer==null) {
            if (indexPath != null){
                createIndex();
            }else{
                return;
            }
        }

        Document document = new Document();
        document.add(new TextField(TOKENS.LOG_LINE_TOKEN.toString(), logLine, Field.Store.YES));
        document.add(new LongField(TOKENS.LOG_LINE_NUMBER_TOKEN.toString(), ++currentLogLineNumber, Field.Store.YES));
        document.add(new TextField(TOKENS.FILENAME_TOKEN.toString(), fileName, Field.Store.YES));
        this.writer.addDocument(document);
        this.writer.commit();
    }

    private void createIndex() throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory directory = new RAMDirectory();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
        this.directory = directory;
        this.writer = new IndexWriter(directory, indexWriterConfig);

    }

    public void close() throws IOException {
        if(writer!=null)
            this.writer.close();


    }

    public Directory getDirectory(){
        return directory;
    }
}
