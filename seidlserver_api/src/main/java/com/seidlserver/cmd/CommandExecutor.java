package com.seidlserver.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 14:58
*/
public class CommandExecutor {
    public static String execute(String... args) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        Process process = builder.start();
        StringBuilder output = new StringBuilder();
        StringBuilder error = new StringBuilder();

        //Stdout
        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while((line = br.readLine()) != null){
            output.append(line);
        }

        //Stderror
        br = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
        line = null;
        while((line = br.readLine()) != null){
            error.append(line);
        }

        int exitVal = process.waitFor();
        if(exitVal == 0){
            return output.toString();
        }else{
            throw new IOException("\nStderror:\n"+error.toString()+"Stdout:\n"+output);
        }
    }
}


