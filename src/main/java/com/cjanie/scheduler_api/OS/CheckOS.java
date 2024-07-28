package com.cjanie.scheduler_api.OS;

public class CheckOS {
    public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
}
