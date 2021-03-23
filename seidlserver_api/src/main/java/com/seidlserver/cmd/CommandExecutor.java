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

    public static String execute(String dir, String command) throws IOException, InterruptedException {
        output="";
        if(!dir.endsWith("/")){
            dir+="/";
        }
        Process process;
        process = Runtime.getRuntime().exec(String.format(dir+" "+command));
        String lol = "";
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
