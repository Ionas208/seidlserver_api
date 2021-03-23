package com.seidlserver.cmd;

import java.io.IOException;
import java.util.concurrent.Executors;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 14:58
*/
public class CommandExecutor {

    public static String output = "";

    public static String execute(String command) throws IOException, InterruptedException {
        output="";
        Process process;
        process = Runtime.getRuntime().exec(command);
        IOStreamHandler handler = new IOStreamHandler(process.getInputStream(), CommandExecutor::buildOutput);
        Executors.newSingleThreadExecutor().submit(handler);
        int exitCode = process.waitFor();
        assert exitCode == 0;
        return output;
    }

    private static void buildOutput(String s){
        output+=s;
    }
}
