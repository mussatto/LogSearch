package com.curupira.index;

public enum TOKENS {
    LOG_LINE_TOKEN("log_line"),
    LOG_LINE_NUMBER_TOKEN("log_line_number"),
    FILENAME_TOKEN("filename");

    private String tokenName;

    private TOKENS(String tokenName){
        this.tokenName=tokenName;
    }

    @Override
    public String toString(){
        return tokenName;
    }
}
