package com.curupira.document;

import com.curupira.index.TOKENS;
import org.apache.lucene.document.Document;

public class LogSearchDocument {

    private final Document document;

    public LogSearchDocument(Document document){
        this.document=document;
    }

    public String getFilename(){
        return document.get(TOKENS.FILENAME_TOKEN.toString());
    }

    public String getLogLineNumber(){
        return document.get(TOKENS.LOG_LINE_NUMBER_TOKEN.toString());
    }

    public String getLog(){
        return document.get(TOKENS.LOG_LINE_TOKEN.toString());
    }

    public String getSearchResult(){
        return toString();
    }

    @Override
    public String toString(){
        return getFilename() +
                ","+ getLogLineNumber()+
                ": "+ getLog();
    }
}
