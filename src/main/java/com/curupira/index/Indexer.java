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

    private String indexPath;

    private IndexWriter writer;

    private Directory directory;

    private long currentLogLineNumber;

    enum TOKENS{
        LOG_LINE_TOKEN("log_line"),
        LOG_LINE_NUMBER_TOKEN("log_line_number"),
        FILENAME_TOKEN("log_line_number");

        private String tokenName;

        private TOKENS(String tokenName){
            this.tokenName=tokenName;
        }

        @Override
        public String toString(){
            return tokenName;
        }
    }
    public static final String LOG_LINE_TOKEN="log_line";
    public static final String LOG_LINE_NUMBER_TOKEN="log_line_number";
    public static final String FILENAME_TOKEN="log_line_number";

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
        document.add(new TextField(LOG_LINE_TOKEN, logLine, Field.Store.YES));
        document.add(new LongField(LOG_LINE_NUMBER_TOKEN, ++currentLogLineNumber, Field.Store.YES));
        document.add(new TextField(FILENAME_TOKEN, fileName, Field.Store.YES));
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
