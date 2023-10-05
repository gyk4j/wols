package net.altkey12.wols.util;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.text.StdoutExtractor;
import net.altkey12.wols.text.StdoutParser;

public class CommandLineTool<T> implements Callable<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineTool.class);
	
	protected String[] command;
	protected Map<String, String> map;
	protected StdoutParser parser;
	protected StdoutExtractor<T> extractor;

	/**
	 * 
	 * @param command
	 * @param parser
	 * @param extractor
	 */
	public CommandLineTool(String[] command, StdoutParser parser, StdoutExtractor<T> extractor) {
		this.command = command;
		this.parser = parser;
		this.extractor = extractor;
	}

	public String[] getCommand() {
		return command;
	}

	public void setCommand(String[] command) {
		this.command = command;
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public StdoutParser getParser() {
		return parser;
	}

	public void setParser(StdoutParser parser) {
		this.parser = parser;
	}

	public StdoutExtractor<T> getExtractor() {
		return extractor;
	}

	public void setExtractor(StdoutExtractor<T> extractor) {
		this.extractor = extractor;
	}
	
	public final T execute() throws RuntimeException {
		T data;
		CommandLineOutputs o = CommandLineRunner.run(getCommand());
		setMap(getParser().parse(o.getStdout()));
		data = getExtractor().extract(getMap());
		
		if(o.getExitCode() != 0) {
			String msg = String.join(" ", getCommand()) + " exited with code " + o.getExitCode() + " : " + o.getStderr();
			LOGGER.warn(msg);
			throw new RuntimeException(msg);
		}
		return data;
	}

	@Override
	public T call() throws Exception {
		return execute();
	}
}
