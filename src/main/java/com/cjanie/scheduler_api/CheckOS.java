package com.cjanie.scheduler_api;

public class CheckOS {
    static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
}
