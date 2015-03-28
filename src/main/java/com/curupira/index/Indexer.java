package com.curupira.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public abstract class Indexer {

    protected final String indexPath;

    protected IndexWriter writer;

    protected Directory directory;

    protected long currentLogLineNumber;


    public static Indexer createInFileSystemExisting(String indexPath){
        return new FSIndexer(indexPath);
    }

    public static Indexer createInMemory(String indexPath){

        return new RAMIndexer(indexPath);
    }

    public Indexer(String path){
        this.indexPath=path;
        try {
            createIndex(indexPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void indexLogLine(String logLine, String fileName) throws IOException {
        if(writer==null) {
            if (indexPath != null){
                createIndex(indexPath);
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

    abstract void createIndex(String indexPath) throws IOException;

    public void close() throws IOException {
        if(writer!=null)
            this.writer.close();
    }

    public Directory getDirectory(){
        return directory;
    }
}
