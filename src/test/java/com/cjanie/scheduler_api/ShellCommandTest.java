package com.cjanie.scheduler_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import com.cjanie.scheduler_api.OS.CheckOS;
import com.cjanie.scheduler_api.OS.ShellCommand;

public class ShellCommandTest {
    
    @Test
    public void runOsCommand() throws IOException, InterruptedException {
        String[] command;

        // TODO command factory
		if(CheckOS.isWindows) {
			command = new String[] {"cmd.exe", "/c", "dir"};
		} else {
			command = new String[] {"sh", "-c", "ls"};
		}

        int exitCode = ShellCommand.runCmd(command);
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
