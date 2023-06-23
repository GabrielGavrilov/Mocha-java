package com.gabrielgavrilov.mocha;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Mocha - A tiny flexible web server framework for Java
 * @author Gabriel Gavrilov
 */

public class Mocha extends MochaRoutes {

	/**
	 * Sets the Mocha server setting to the given setting value.
	 *
	 * @param setting Name of the setting.
	 * @param settingValue Value of the setting.
	 */
	public static void set(String setting, String settingValue) {
		switch(setting.toLowerCase()) {
			case "views":
				MochaServerAttributes.VIEWS_DIRECTORY = settingValue;
				break;
			case "static":
				MochaServerAttributes.STATIC_DIRECTORY = settingValue;
				break;
		}
	}

	/**
	 * Starts the Mocha web server, and listens for new sockets.
	 *
	 * @param port Port for the server.
	 * @param callback Callback method to be executed once this server is running.
	 */
	public static void listen(int port, Runnable callback) {
		try {
			callback.run();
			ServerSocket mochaServer = new ServerSocket(port);
			while(true) {
				try {

					Socket client = mochaServer.accept();

					InputStream clientInput = client.getInputStream();
					OutputStream clientOutput = client.getOutputStream();

					new MochaClient(clientInput, clientOutput);

					client.close();

				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: Add a Mocha listener with an internet protocol address option.

}
