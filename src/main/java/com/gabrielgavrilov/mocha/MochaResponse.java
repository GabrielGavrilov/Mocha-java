package com.gabrielgavrilov.mocha;

import java.io.IOException;
import java.io.OutputStream;

public class MochaResponse {

	/**
	 * Renders the given data to the client as HTML.
	 * @param data Data that will be rendered as HMTL.
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
