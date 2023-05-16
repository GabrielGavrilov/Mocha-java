package com.gabrielgavrilov.mocha;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Mocha - A tiny flexible web server framework for Java
 * @author Gabriel Gavrilov
 */

public class Mocha extends MochaRoutes {

	/**
	 * Sets the Mocha server setting to the given setting value.
	 * @param setting Name of the setting.
	 * @param settingValue Value of the setting.
	 */
	public void set(String setting, String settingValue) {
		switch(setting.toLowerCase()) {
			case "views":
				MochaServerAttributes.VIEWS_DIRECTORY = settingValue;
		}
	}

	/**
	 * Starts the Mocha web server, and listens for new sockets.
	 * @param port Port for the server.
	 * @param callback Callback method to be executed once this server is running.
	 */
	public void listen(int port, Runnable callback) {
		try {
			callback.run();
			ServerSocket mochaServer = new ServerSocket(port);
			while(true) {
				new Thread(()-> {
					try {
						new MochaClient(mochaServer.accept());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}).run();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: Add a Mocha listener with an internet protocol address option.

}
