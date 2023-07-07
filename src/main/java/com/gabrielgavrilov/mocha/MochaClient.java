package com.gabrielgavrilov.mocha;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MochaClient {

	public static InputStream INPUT_STREAM;
	public static OutputStream OUTPUT_STREAM;
	public static HashMap<String, String> PAYLOAD = new HashMap<>();

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
			StringBuilder header = new StringBuilder();
			StringBuilder payloads = new StringBuilder();

			String line;
			while((line = buffer.readLine()).length() != 0) {
				header.append(line + "\r\n");
			}

			if(getMethod(header.toString()).equals("POST")) {
				// Get the payload data
				while(buffer.ready()) {
					payloads.append((char) buffer.read());
				}

				String rawPayloads[] = payloads.toString().split("&");

				for(int i = 0; i < rawPayloads.length; i++) {
					String payload[] = rawPayloads[i].split("=");

					payload[1] = payload[1].replace('+', ' ');

					PAYLOAD.put(payload[0], payload[1]);
				}


				handlePostRoutes(header.toString());
				handleStaticRoutes(header.toString());
			}

			else {
				handleGetRoutes(header.toString());
				handleStaticRoutes(header.toString());
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Route handler for the client. Used to link the requested route to the appropriate response.
	 *
	 * @param request Socket request.
	 */
	protected void handleGetRoutes(String header) {
		String requestedDirectory = header.split("\r\n")[0].split(" ")[1];
		//handleStaticRoutes(requestedDirectory);

		for(int i = 0; i < MochaServerAttributes.GET_DIRECTORIES.size(); i++) {
			String directory = MochaServerAttributes.GET_DIRECTORIES.get(i);
			if(directory.equals(requestedDirectory)) {
				MochaServerAttributes.GET_DIRECTORY_CALLBACKS.get(i).accept(new MochaResponse());
			}
		}

	}

	protected void handlePostRoutes(String header) {
		String requestedDirectory = header.split("\r\n")[0].split(" ")[1];

		for(int i = 0; i < MochaServerAttributes.POST_DIRECTORIES.size(); i++) {
			String directory = MochaServerAttributes.POST_DIRECTORIES.get(i);
			if(directory.equals(requestedDirectory)) {
				MochaServerAttributes.POST_DIRECTORY_CALLBACKS.get(i).accept(new MochaRequest(), new MochaResponse());
			}
		}

	}

	/**
	 * Static route handler for the client. Used to link requested static routes to the appropriate response.
	 *
	 * @param request Socket request.
	 */
	// TODO: Make this prettier.
	protected void handleStaticRoutes(String header) {
		String r = header.split("\r\n")[0].split(" ")[1];
		String requestedDirectory = MochaServerAttributes.STATIC_DIRECTORY + r.substring(1);

		for(int i = 0; i < MochaServerAttributes.STATIC_DIRECTORIES.size(); i++) {

			String directory = MochaServerAttributes.STATIC_DIRECTORIES.get(i);

			if(directory.equals(requestedDirectory)) {
				MochaServerAttributes.STATIC_DIRECTORY_CALLBACKS.get(i).accept(new MochaResponse());
			}
		}

	}

	protected String getMethod(String header) {
		return header.split("\r\n")[0].split(" ")[0];
	}

}
