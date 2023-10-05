package net.altkey12.wols.util.platform.linux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import net.altkey12.wols.beans.speedtest.linux.OutputDeviceInfoTool;
import net.altkey12.wols.controller.Controller;

public class DeviceInfoTool implements Callable<OutputDeviceInfoTool> {

	@Override
	public OutputDeviceInfoTool call() throws Exception {
		
		String sysVendor = Files.readAllLines(Paths.get("/sys/class/dmi/id/sys_vendor")).stream().collect(Collectors.joining());
		String productName = Files.readAllLines(Paths.get("/sys/class/dmi/id/product_name")).stream().collect(Collectors.joining());
		
		List<String> etcOsRelease = Files.readAllLines(Paths.get("/etc/os-release"));
		
		String caption = etcOsRelease.stream()
				.filter(s -> s.startsWith("NAME="))
				.map(s -> s.substring(s.indexOf('=')+1).replaceAll("\"", "").trim())
				.collect(Collectors.joining());
		String version = etcOsRelease.stream()
				.filter(s -> s.startsWith("VERSION="))
				.map(s -> s.substring(s.indexOf('=')+1).replaceAll("\"", "").trim())
				.collect(Collectors.joining());
		
//		String serialNumber = Files.readAllLines(Paths.get("/etc/machine-id")).stream().collect(Collectors.joining());
//		String buildNumber = Files.readAllLines(Paths.get("/etc/machine-id")).stream().collect(Collectors.joining());
		
		Controller c = Controller.getInstance();
		String serialNumber = c.getApplicationId().toString();
		String buildNumber = c.getApplicationVersion();
		
		return new OutputDeviceInfoTool(
				sysVendor, 
				productName, 
				caption, 
				version, 
				serialNumber, 
				buildNumber);
	}

}
