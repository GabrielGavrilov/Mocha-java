package com.gabrielgavrilov.mocha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MochaClient {

	public static Socket SOCKET;

	// TODO: Fix the refresh issue.

	/**
	 * Constructor for the Mocha client. Used to accept and render HTTP/3 responses to the
	 * given socket.
	 *
	 * @param socket Client socket.
	 */
	protected MochaClient(Socket socket) {
		try {
			SOCKET = socket;

			InputStreamReader clientInput = new InputStreamReader(socket.getInputStream());
			BufferedReader buffer = new BufferedReader(clientInput);
			StringBuilder request = new StringBuilder();

			String line;
			while(!(line = buffer.readLine()).isBlank()) {
				request.append(line + "\r\n");
			}

			handleRoutes(request.toString());
			handleStaticRoutes(request.toString());

			socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Route handler for the client. Used to link the requested route to the appropriate response.
	 *
	 * @param request Socket request.
	 */
	protected void handleRoutes(String request) {
		String requestedDirectory = request.split("\r\n")[0].split(" ")[1];
		//handleStaticRoutes(requestedDirectory);

		for(int i = 0; i < MochaServerAttributes.DIRECTORIES.size(); i++) {
			String directory = MochaServerAttributes.DIRECTORIES.get(i);
			if(directory.equals(requestedDirectory)) {
				MochaServerAttributes.DIRECTORY_CALLBACKS.get(i).accept(new MochaResponse());
			}
		}

		System.out.println("Done. 1");
	}

	/**
	 * Static route handler for the client. Used to link requested static routes to the appropriate response.
	 *
	 * @param request Socket request.
	 */
	// TODO: Make this prettier.
	protected void handleStaticRoutes(String request) {
		String r = request.split("\r\n")[0].split(" ")[1];
		String requestedDirectory = MochaServerAttributes.STATIC_DIRECTORY + r.substring(1);

		for(int i = 0; i < MochaServerAttributes.STATIC_DIRECTORIES.size(); i++) {

			String directory = MochaServerAttributes.STATIC_DIRECTORIES.get(i);

			if(directory.equals(requestedDirectory)) {
				MochaServerAttributes.STATIC_DIRECTORY_CALLBACKS.get(i).accept(new MochaResponse());
			}
		}

		System.out.println("done. 2");

	}

}
