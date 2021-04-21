package com.seidlserver.cmd;

import java.io.IOException;

/*
    Created by: Jonas Seidl
    Date: 14.04.2021
    Time: 18:48
*/
public class Commands {
    public static final String[] memTotal = new String[]{"cat", "/proc/meminfo", "|", "grep", "\"^MemTotal*\"", "|", "grep", "-Eo \"[0-9]*[0-9]\""};
    public static final String[] memFree = new String[]{"cat", "/proc/meminfo", "|", "grep", "\"^MemFree*\"", "|", "grep", "-Eo \"[0-9]*[0-9]\""};
    public static final String[] shutdown = new String[]{"sudo", "shutdown", "-P", "now"};
    public static final String[] restart = new String[]{"sudo", "restart"};
    public static final String[] cpu = new String[]{"mpstat", "-o", "JSON"};
    public static final String[] test = new String[]{"wsl", "cat", "/proc/meminfo"};

    public static void main(String[] args) {
        try {
            String s = CommandExecutor.execute(test);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}