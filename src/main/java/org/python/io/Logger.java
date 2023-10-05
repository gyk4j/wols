package org.python.io;

import static org.python.lang.global.VERBOSE;

import org.slf4j.LoggerFactory;

public class Logger {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(org.python.io.Logger.class);
	
	public static void errprint(String m) {
//		System.err.println(m);
		LOGGER.error(m);
	}
	
	public static void LOG(String m) {
		if (VERBOSE) {
			errprint(m);
		}
	}
	
	public static int errquit(String m) {
		errprint("Error: " + m);
	    return 1;
	}
}
