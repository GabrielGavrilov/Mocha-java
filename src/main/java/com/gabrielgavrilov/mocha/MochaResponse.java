package com.gabrielgavrilov.mocha;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MochaResponse {

	/**
	 * Sends the given data to the client with the content-type of text/html.
	 *
	 * @param data Data that will be sent.
	 */
	public void send(String data) {
		send(data, "text/html");
	}

	/**
	 * Renders the given file to the client with the content-type of text/html.
	 *
	 * @param file Name of the file that will be rendered.
	 */
	public void render(String file) {
		try {
			String fileContent = Files.readString(Paths.get(MochaServerAttributes.VIEWS_DIRECTORY + file));
			new MochaScanner(fileContent);
			send(fileContent, "text/html");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends the given data to the client with the content-type of application/json.
	 *
	 * @param data Data that will be sent.
	 */
	public void sendJson(String data) {
		send(data, "application/json");
	}

	/**
	 * Renders the given file to the client with the content-type of application/json
	 *
	 * @param file Name of the file that will be rendered.
	 */
	public void renderJson(String file) {
		try {
			String fileContent = Files.readString(Paths.get(MochaServerAttributes.STATIC_DIRECTORY + file));
			send(fileContent, "application/json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Used to initialize the header.
	 *
	 * @param response Client output stream.
	 * @param status HTTP/3 status.
	 * @param contentType Document content type.
	 * @throws IOException
	 */
	private void initializeHeader(OutputStream response, String status, String contentType) throws IOException {
		response.write(("HTTP/3 " + status + "\r\n").getBytes());
		response.write(("Content-Type: " + contentType + "\r\n").getBytes());
		response.write("\r\n".getBytes());
	}

	/**
	 * Sends the given data to the client as the given content type.
	 *
	 * @param data Data that will be sent.
	 */
	private void send(String data, String contentType) {
		try {
			OutputStream response = MochaClient.SOCKET.getOutputStream();
			initializeHeader(response, "200 OK", contentType);
			response.write(data.getBytes());
			closeHeader(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Used to close the header.
	 *
	 * @param response Client output stream.
	 * @throws IOException
	 */
	private void closeHeader(OutputStream response) throws IOException {
		response.write("\r\n".getBytes());
		response.flush();
		response.close();
	}

	// TODO: Make this dynamic
	protected void renderStylesheet(String file) {
		try {
			String fileContent = Files.readString(Paths.get(MochaServerAttributes.STATIC_DIRECTORY + file));
			new MochaScanner(fileContent);
			send(fileContent, "text/css");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
