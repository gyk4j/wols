package gyk4j.wols.util;

public class CommandLineOutputs {
	protected int exitCode;
	protected String stdout;
	protected String stderr;
	
	public CommandLineOutputs(int exitCode) {
		super();
		this.exitCode = exitCode;
	}
	
	public CommandLineOutputs(int exitCode, String stdout, String stderr) {
		this(exitCode);
		this.stdout = stdout;
		this.stderr = stderr;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public String getStderr() {
		return stderr;
	}

	public void setStderr(String stderr) {
		this.stderr = stderr;
	}
}
