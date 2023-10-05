package net.altkey12.wols.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineRunner.class);
	
	public static CommandLineOutputs run(String... command) {
		StringBuilder stdout = new StringBuilder();
		StringBuilder stderr = new StringBuilder();
		
		CommandLineOutputs output = run(
				s -> stdout.append(s).append(System.lineSeparator()),
				s -> stderr.append(s).append(System.lineSeparator()),
				command);
		output.setStdout(stdout.toString());
		output.setStderr(stderr.toString());
		
		return output;
	}
	
	public static CommandLineOutputs run(Consumer<String> stdoutConsumer, Consumer<String> stderrConsumer, String... command) {
		CommandLineOutputs out = null;
		int exitVal = -1;
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);
		
		try {

			Process process = processBuilder.start();

			/*
			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			*/
			
			
			StreamEater stdoutEater = new StreamEater(
					process.getInputStream(), 
					stdoutConsumer);
			
			StreamEater stderrEater = new StreamEater(
					process.getErrorStream(), 
					stderrConsumer);
			
			stdoutEater.start();
			stderrEater.start();
			
			exitVal = process.waitFor();
			stdoutEater.join();
			stderrEater.join();
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		
		out = new CommandLineOutputs(exitVal);
		
		return out;
	}
	
	static class StreamEater extends Thread {
		BufferedReader br;
		Consumer<String> c;

		public StreamEater(InputStream is, Consumer<String> c) {
			this.br = new BufferedReader(new InputStreamReader(is));
			this.c = c;
		}

		public void run() {
			try {
				String line;
				while ((line = br.readLine()) != null) {
					c.accept(line);
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			} finally {
				try {
					br.close();
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}
}
