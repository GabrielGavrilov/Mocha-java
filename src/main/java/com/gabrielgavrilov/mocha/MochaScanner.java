package com.gabrielgavrilov.mocha;

/**
 * Mocha scanner is used to scan for static files such as CSS, and JavaScript.
 */

public class MochaScanner {

	private String source;
	private int sourceLength;
	private char currentChar;
	private int currentPos;

	/**
	 * Initializes the scanner.
	 *
	 * @param source Source code for the scanner.
	 */
	MochaScanner(String source) {
		this.source = source;
		this.sourceLength = source.length();
		this.currentPos = -1;
		advance();
		scan();
	}

	/**
	 * Advances the current position of the scanner by one.
	 */
	private void advance() {
		this.currentPos++;

		if(this.currentPos >= this.sourceLength)
			this.currentChar = '\0';

		else
			this.currentChar = this.source.charAt(this.currentPos);
	}

	// Not used yet.
	private char peek() {
		if(this.currentChar + 1 >= this.sourceLength)
			return '\0';

		return this.source.charAt(this.currentPos + 1);
	}

	private void skipWhitespace() {
		while(this.currentChar == ' ' || this.currentChar == '\t') {
			advance();
		}
	}

	/**
	 * Scans the current source code, and executes the appropriate methods.
	 */
	private void scan() {
		while(this.currentChar != '\0') {

			if(this.currentChar == '\"') {
				String temp = "";
				advance();

				while(this.currentChar != '\"') {
					temp += currentChar;
					advance();
				}
				determineStaticFileType(temp);
			}

			advance();
		}
	}

	/**
	 * Determines the type of file if the file is static, and execute the appropriate methods.
	 *
	 * @param file File name.
	 */
	private void determineStaticFileType(String file) {
		if(file.contains(".")) {
			String type = file.split("\\.")[1];

			switch (type) {
				case "css":
					handleStylesheetFile(file);
					break;
				case "ico":
					handleFavicon(file);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Handles the stylesheet file.
	 *
	 * @param file Stylesheet file name.
	 */
	private void handleStylesheetFile(String file) {
		MochaRoutes.renderStaticDirectory(MochaServerAttributes.STATIC_DIRECTORY + file, (response)-> {
			response.render(file, MochaServerAttributes.STATIC_DIRECTORY, "text/css");
		});
	}

	private void handleFavicon(String file) {
		MochaRoutes.renderStaticDirectory(MochaServerAttributes.STATIC_DIRECTORY + file, (response)-> {
			response.renderImage(file);
		});
	}

}
