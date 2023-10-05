package net.altkey12.wols.net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.speedtest.DiagnosticsInformation;
import net.altkey12.wols.beans.speedtest.Exceptions;
import net.altkey12.wols.beans.speedtest.OutputSpeedTests;
import net.altkey12.wols.beans.speedtest.OutputWanIp;
import net.altkey12.wols.beans.speedtest.SpeedTests;
import net.altkey12.wols.util.SpeedTestsTool;
import net.altkey12.wols.util.WanIpTool;

/**
 * For cross-platform system information gathering functions to be inherited and 
 * shared by other platform-specific 
 * {@link net.altkey12.wols.net.NetworkDiagnostics NetworkDiagnostics}
 * implementations. 
 * @author user
 *
 */
public abstract class AbstractNetworkDiagnostics implements NetworkDiagnostics {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNetworkDiagnostics.class);
	
	private ExecutorService executorService = Executors.newFixedThreadPool(12);
	protected int exceptions = 0;
	
	protected WanIpTool cmdWanIp;
	protected SpeedTestsTool cmdSpeedTests;
	
	protected OutputWanIp wanIp;
	protected OutputSpeedTests speedTests;
	
	protected AbstractNetworkDiagnostics() {
		cmdWanIp = new WanIpTool();
		cmdSpeedTests = new SpeedTestsTool();
		
		initialize();
	}
	
	private void initialize() {
		wanIp = executeSafely(cmdWanIp);
		speedTests = executeSafely(cmdSpeedTests);
	}
	
	protected ExecutorService getExecutorService() {
		return executorService;
	}

	protected void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	protected int getExceptionCount() {
		return exceptions;
	}

	protected void setExceptionCount(int exceptions) {
		this.exceptions = exceptions;
	}

	protected <R> R executeSafely(Callable<R> cmd) {
		R r = null;
		try {
			Future<R> task = executorService.submit(cmd);
			r = task.get();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
			exceptions++;
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage());
			exceptions++;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			exceptions++;
		}
		return r;
	}
	
	@Override
	public DiagnosticsInformation getDiagnosticsInformation() {
		return new DiagnosticsInformation(
				"Full", 
				"Wifi");
	}
	
	@Override
	public SpeedTests getSpeedTests() {
		float speed = (speedTests != null)? 
				speedTests.getDownloadSpeedTest(): -1;
		return new SpeedTests(speed);
	}
	
	@Override
	public Exceptions getExceptions() {
		return new Exceptions(getExceptionCount());
	}
}
