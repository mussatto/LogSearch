package com.curupira.search;

import com.curupira.document.LogSearchDocument;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface LogSearch {

    List<LogSearchDocument> searchString(String queryString) throws ParseException, IOException;
}
