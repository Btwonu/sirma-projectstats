package com.home.projectstats.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader implements AutoCloseable {
    private static final String COMMA_DELIMITER = ",";
    private final BufferedReader bufferedReader;
    private boolean hasHeader;

    public CSVReader(FileReader fileReader, boolean hasHeader) {
        this.bufferedReader = new BufferedReader(fileReader);
        this.hasHeader = hasHeader;
    }

    public String[] readLine() throws IOException {
        String line = bufferedReader.readLine();

        if (hasHeader) {
            hasHeader = false;
            return readLine();
        }

        if (line == null) {
            return null;
        }

        return line.split(COMMA_DELIMITER);
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
