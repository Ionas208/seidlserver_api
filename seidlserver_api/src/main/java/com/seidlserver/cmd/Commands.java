package com.seidlserver.cmd;

/*
    Created by: Jonas Seidl
    Date: 14.04.2021
    Time: 18:48
*/
public class Commands {
    public static final String[] memTotalCommand = new String[]{"cat", "/proc/meminfo", "|", "grep", "\"^MemTotal*\"", "|", "grep", "-Eo \"[0-9]*[0-9]\""};

    public static void main(String[] args) {
        try{
            System.out.println(CommandExecutor.execute(memTotalCommand));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}


