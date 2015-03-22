package com.curupira.index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInputter {

    private BufferedReader bufferedReader;
    private Indexer indexer;
    private String path;

    public FileInputter(Indexer indexer, String path) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        this.indexer=indexer;
        this.path = path;
    }

    public void feedToIndex() throws IOException {
        String line;

        while((line = bufferedReader.readLine()) != null) {
            indexer.indexLogLine(line, path);
        }
    }
}
