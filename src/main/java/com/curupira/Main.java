package com.curupira;

import com.curupira.index.FileInputter;
import com.curupira.index.Indexer;
import com.curupira.index.SimpleSearch;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
	    if(args.length<1)
            System.out.println("Pass the file and string to search as parameter");

        try {
            if (args.length == 1) {
                long start = new Date().getTime();

                String path = args[0];

                Indexer indexer = Indexer.createInMemory("luceneIndex");

                FileInputter fileInputter = new FileInputter(indexer, path);

                fileInputter.feedToIndex();

                long diff = new Date().getTime() - start;

                System.out.println("Indexed file in " + diff + " miliseconds.");

                SimpleSearch simpleSearch = new SimpleSearch(indexer.getDirectory());

                String[] results = simpleSearch.searchString("exception");

                for (String result : results) {
                    System.out.println(result);
                }
            }
        } catch (Exception e){
            System.out.println("Error ocurred - "+e.getMessage());
        }
    }
}
