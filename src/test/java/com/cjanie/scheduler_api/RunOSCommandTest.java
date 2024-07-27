package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RunOSCommandTest {
    
    @Test
    public void runOsCommand() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();

        // TODO command factory
		if(CheckOS.isWindows) {
			builder.command("cmd.exe", "/c", "dir");
		} else {
			builder.command("sh", "-c", "ls");
		}

        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        StreamGobbler streamGobbler = 
        new StreamGobbler(process.getInputStream(), System.out::println);
        ExecutorService executorService = 
        new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,   
        new LinkedBlockingQueue<Runnable>());
        Future<?> future = executorService.submit(streamGobbler);

        int exitCode = process.waitFor();
        assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS));
        assertEquals(0, exitCode); 
    }

    @Test
    public void givenProcessBuilder_whenStartingPipeline_thenSuccess() throws IOException {
        List<ProcessBuilder> builders = Arrays.asList(
            new ProcessBuilder("find", "src", "-name", "*.java", "-type", "f"), 
            new ProcessBuilder("wc", "-l"));

    List<Process> processes = ProcessBuilder.startPipeline(builders);
    Process last = processes.get(processes.size() - 1);

    List<String> output = readOutput(last.getInputStream());
    // Results should not be empty
    assertTrue(!output.isEmpty());
    }

    private List<String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                .collect(Collectors.toList());
        }
    }
}
