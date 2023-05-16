package com.gabrielgavrilov.mocha;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Gabriel Gavrilov
 */

public class Mocha extends MochaRoutes {

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
