package com.cjanie.scheduler_api.OS;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cjanie.scheduler_api.StreamGobbler;

public class ShellCommand {
    public static int runCmd(String... args) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        StreamGobbler streamGobbler = 
        new StreamGobbler(process.getInputStream(), System.out::println);
        ExecutorService executorService = 
        new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,   
        new LinkedBlockingQueue<Runnable>());
        executorService.submit(streamGobbler);
        int exitCode = process.waitFor();
        return exitCode;
    }
}
