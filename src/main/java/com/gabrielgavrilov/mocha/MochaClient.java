package com.gabrielgavrilov.mocha;

import java.io.*;
import java.net.Socket;

public class MochaClient {

	public static InputStream INPUT_STREAM;
	public static OutputStream OUTPUT_STREAM;

	/**
	 * Constructor for the Mocha client. Used to accept and render HTTP/3 responses to the
	 * given socket.
	 *
	 * @param input Socket InputStream
	 * @param output Socket OutputStream
	 */
	protected MochaClient(InputStream input, OutputStream output) {
		try {
			INPUT_STREAM = input;
			OUTPUT_STREAM = output;

			InputStreamReader clientInput = new InputStreamReader(INPUT_STREAM);
			BufferedReader buffer = new BufferedReader(clientInput);
			StringBuilder request = new StringBuilder();

			String line;
			while(!(line = buffer.readLine()).isBlank()) {
				request.append(line + "\r\n");
			}

			handleRoutes(request.toString());
			handleStaticRoutes(request.toString());

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

	}

}
