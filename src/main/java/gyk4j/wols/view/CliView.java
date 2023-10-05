package gyk4j.wols.view;

import static gyk4j.wols.controller.CliController.main;
import static org.python.io.Logger.errprint;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.python.lang.error.HTTPNotFoundExn;
import org.python.lang.error.MalformedResponseExn;
import org.python.lang.error.ServerErrorExn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliView implements IUserInterface {
	public static final Logger LOGGER = LoggerFactory.getLogger(CliView.class);

	@Override
	public void start() {
		try {
			System.exit(main());
		} catch (HTTPNotFoundExn e) {
	        errprint(String.format("HTTP error: %s", e.getMessage()));
	        System.exit(1);
	    } catch (MalformedResponseExn e) {
	        errprint(String.format("Malformed response from server: %s", e.getMessage()));
	        System.exit(1);
		} catch (ServerErrorExn e) {
	        errprint(String.format("Server responded with error message: %s", e.getMessage()));
	        System.exit(1);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		LOGGER.info("Stopping...");
	}
}
