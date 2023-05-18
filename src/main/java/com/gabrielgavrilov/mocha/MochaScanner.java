package com.gabrielgavrilov.mocha;

/**
 * Mocha scanner is used to scan for static files such as CSS, and JavaScript.
 */

public class MochaScanner {

	private String source;
	private int sourceLength;
	private char currentChar;
	private int currentPos;

	MochaScanner(String source) {
		this.source = source;
		this.sourceLength = source.length();
		this.currentPos = -1;
		advance();
		scan();
	}

	private void advance() {
		this.currentPos++;

		if(this.currentPos >= this.sourceLength)
			this.currentChar = '\0';

		else
			this.currentChar = this.source.charAt(this.currentPos);
	}

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

	private void scan() {
		while(this.currentChar != '\0') {

			if(this.currentChar == '\"') {
				String temp = "";
				advance();

				while(this.currentChar != '\"') {
					temp += currentChar;
					advance();
				}

				System.out.println(temp);
			}

			advance();
		}
	}

}
