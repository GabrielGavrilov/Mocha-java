package com.gabrielgavrilov.mocha;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MochaResponse {

	/**
	 * Renders the given data to the client as HTML.
	 * @param data Data that will be rendered as HTML.
	 */
	public void send(String data) {
		try {
			OutputStream response = MochaClient.SOCKET.getOutputStream();
			initializeHeader(response, "200 OK", "text/html");
			response.write(data.getBytes());
			closeHeader(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Renders the given file to the client as HTML.
	 * @param file Name of the file to render.
	 */
	public void render(String file) {
		try {
			OutputStream response = MochaClient.SOCKET.getOutputStream();
			String fileContent = Files.readString(Paths.get(MochaServerAttributes.VIEWS_DIRECTORY + file));

			initializeHeader(response, "200 OK", "text/html");
			response.write(fileContent.getBytes());
			closeHeader(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Used to initialize the header.
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
	 * Used to close the header.
	 * @param response Client output stream.
	 * @throws IOException
	 */
	private void closeHeader(OutputStream response) throws IOException {
		response.write("\r\n".getBytes());
		response.flush();
		response.close();
	}

}
