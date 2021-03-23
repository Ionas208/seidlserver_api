package com.seidlserver.cmd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 14:58
*/
public class IOStreamHandler implements Runnable{
    private InputStream inputStream;
    private Consumer<String> consumer;

    public IOStreamHandler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines()
                .forEach(consumer);
    }
}
