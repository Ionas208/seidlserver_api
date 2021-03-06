package com.seidlserver.cmd;

import java.io.IOException;

/*
    Created by: Jonas Seidl
    Date: 14.04.2021
    Time: 18:48
*/

/***
 * This class contains all predefined commands
 */
public class Commands {
    public static final String[] mem = new String[]{"cat", "/proc/meminfo"};

    public static final String[] shutdown = new String[]{"sudo", "shutdown", "-P", "now"};
    public static final String[] restart = new String[]{"sudo", "reboot"};

    public static final String[] cpu = new String[]{"mpstat", "-o", "JSON"};
    public static final String[] test = new String[]{"wsl", "cat", "/proc/meminfo"};
}
