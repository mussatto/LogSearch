package com.curupira.index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileInputter {

    private BufferedReader bufferedReader;
    private LogIndexer logIndexer;
    private String path;

    public FileInputter(LogIndexer logIndexer, String path) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(path));
        this.logIndexer = logIndexer;
        this.path = path;
    }

    public void feedToIndex() throws IOException {
        String line;

        while((line = bufferedReader.readLine()) != null) {
            logIndexer.indexLogLine(line, path);
        }
    }
}
