package com.seidlserver.cmd;

import java.io.IOException;
import java.util.concurrent.Executors;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 14:58
*/
public class CommandExecutor {
    public static void execute() throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String homeDirectory = System.getProperty("user.home");
        Process process;
        if (isWindows) {
            process = Runtime.getRuntime()
                    .exec(String.format("cmd.exe /c dir %s", homeDirectory));
        } else {
            process = Runtime.getRuntime()
                    .exec(String.format("sh -c ls %s", homeDirectory));
        }
        IOStreamHandler handler =
                new IOStreamHandler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(handler);
        int exitCode = process.waitFor();
        assert exitCode == 0;
    }
}
